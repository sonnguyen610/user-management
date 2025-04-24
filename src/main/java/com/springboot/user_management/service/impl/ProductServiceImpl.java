package com.springboot.user_management.service.impl;

import com.springboot.user_management.config.SecurityUtils;
import com.springboot.user_management.constant.FailureMessage;
import com.springboot.user_management.constant.SuccessMessage;
import com.springboot.user_management.constant.ValidationMessage;
import com.springboot.user_management.dto.request.ProductRequestDTO;
import com.springboot.user_management.dto.request.ProductSearchDTO;
import com.springboot.user_management.dto.response.ProductResponseDTO;
import com.springboot.user_management.dto.response.paging.Metadata;
import com.springboot.user_management.dto.response.paging.ProductResponsePagingDTO;
import com.springboot.user_management.entity.Brand;
import com.springboot.user_management.entity.Category;
import com.springboot.user_management.entity.Product;
import com.springboot.user_management.mapper.response.ProductResponseDtoMapper;
import com.springboot.user_management.repository.BrandRepository;
import com.springboot.user_management.repository.CategoryRepository;
import com.springboot.user_management.repository.ProductRepository;
import com.springboot.user_management.repository.custom.ProductCustomRepository;
import com.springboot.user_management.service.ProductService;
import com.springboot.user_management.utils.BaseResponse;
import com.springboot.user_management.utils.ResponseFactory;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductResponseDtoMapper productResponseDtoMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductCustomRepository productCustomRepository;

    @Override
    public ResponseEntity<BaseResponse<List<ProductResponseDTO>>> findAllProduct() {
        try {
            List<Product> productList = productRepository.findAll();
            List<ProductResponseDTO> response = productResponseDtoMapper.toListDTO(productList);
            return ResponseFactory.success(HttpStatus.OK, response, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, FailureMessage.FAILURE);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<ProductResponsePagingDTO>> findAllProductByNameAndStatusAndDate(String name, Boolean status, String startDate, String endDate, String sortBy, String sortType, Integer page, Integer size) {
        try {
            String username = SecurityUtils.getUsername();

            if (sortBy == null || sortBy.trim().isEmpty()) {
                sortBy = "createdAt";
            } else {
                sortBy = sortBy.trim();
            }

            Sort.Direction direction = (sortType == null || sortType.isEmpty() || sortType.equalsIgnoreCase("DESC"))
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

            startDate = LocalDate.parse(startDate).atStartOfDay()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            endDate = LocalDate.parse(endDate).atTime(23, 59, 59)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));
            Page<Product> productPage = productRepository.findAllByNameAndStatusAndDate(name, status, startDate, endDate, username, pageable);
            List<ProductResponseDTO> responseDTOList = productPage.getContent().stream().map(productResponseDtoMapper::toDTO).collect(Collectors.toList());
            ProductResponsePagingDTO responsePagingDTO = new ProductResponsePagingDTO(Metadata.build(productPage), responseDTOList);
            return ResponseFactory.success(HttpStatus.OK, responsePagingDTO, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, FailureMessage.FAILURE);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<ProductResponsePagingDTO>> findAllProductByConditions(ProductSearchDTO dto, Integer page, Integer size) {
        try {
            ProductResponsePagingDTO response = productCustomRepository.findAllProductByConditions(dto, page, size);
            return ResponseFactory.success(HttpStatus.OK, response, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, FailureMessage.FAILURE);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<ProductResponseDTO>> createProduct(ProductRequestDTO dto) {
        try {
            dto.trimFields();

            Product product = new Product();
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            product.setQuantity(dto.getQuantity() == null ? 0 : dto.getQuantity());

            Category category = categoryRepository.getReferenceById(dto.getCategory());
            product.setCategory(category);

            Brand brand = brandRepository.getReferenceById(dto.getBrand());
            product.setBrand(brand);

            productRepository.save(product);
            product.setCode(generateProductCode(brand.getCode(), category.getCode(), product.getId()));
            productRepository.save(product);
            ProductResponseDTO responseDTO = productResponseDtoMapper.toDTO(product);
            return ResponseFactory.success(HttpStatus.OK, responseDTO, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<ProductResponseDTO>> viewProduct(Integer id) {
        try {
            if (!productRepository.existsById(id)) {
                throw new BadRequestException(FailureMessage.PRODUCT_NOT_FOUND);
            }

            Product product = productRepository.getReferenceById(id);
            ProductResponseDTO responseDTO = productResponseDtoMapper.toDTO(product);
            return ResponseFactory.success(HttpStatus.OK, responseDTO, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<ProductResponseDTO>> changeStatus(Integer id, Boolean status) {
        try {
            if (!productRepository.existsById(id)) {
                throw new BadRequestException(FailureMessage.DATA_NOT_FOUND);
            }

            Product product = productRepository.getReferenceById(id);
            product.setStatus(status);
            product.setOriginalStatus(status);
            productRepository.save(product);
            ProductResponseDTO responseDTO = productResponseDtoMapper.toDTO(product);
            return ResponseFactory.success(HttpStatus.OK, responseDTO, SuccessMessage.STATUS_CHANGED);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<ProductResponseDTO>> updateProduct(Integer id, ProductRequestDTO dto) {
        try {
            if (!productRepository.existsById(id)) {
                throw new BadRequestException(FailureMessage.DATA_NOT_FOUND);
            }

            if (productRepository.existsByNameAndIdNot(dto.getName(), id)) {
                throw new BadRequestException(ValidationMessage.NAME_EXISTS);
            }

            Product product = productRepository.getReferenceById(id);
            if (!product.getStatus()) {
                throw new BadRequestException(FailureMessage.PRODUCT_INACTIVATED);
            }

            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            if (!Objects.equals(product.getBrand().getId(), dto.getBrand())) {
                Brand brand = brandRepository.getReferenceById(dto.getBrand());
                product.setBrand(brand);
            }
            if (!Objects.equals(product.getCategory().getId(), dto.getCategory())) {
                Category category = categoryRepository.getReferenceById(dto.getCategory());
                product.setCategory(category);
            }
            productRepository.save(product);
            ProductResponseDTO responseDTO = productResponseDtoMapper.toDTO(product);
            return ResponseFactory.success(HttpStatus.OK, responseDTO, SuccessMessage.DATA_UPDATED);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<ProductResponseDTO>> deleteProducts(List<Integer> ids) {
        try {
            if (ids == null || ids.isEmpty()) {
                throw new BadRequestException(FailureMessage.PRODUCT_LIST_NOT_SELECTED);
            }

            List<Product> productList = productRepository.findAllByIds(ids);
            productRepository.deleteAll(productList);
            return ResponseFactory.success(HttpStatus.OK, null, SuccessMessage.DELETE_SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public Map<String, String> validateProduct(ProductRequestDTO dto) {
        dto.trimFields();
        Map<String, String> errors = new HashMap<>();

        if (dto.getName() != null && productRepository.existsByName(dto.getName())) {
            errors.put("name", ValidationMessage.NAME_EXISTS);
        }

        if (dto.getCategory() != null && !categoryRepository.existsByIdAndStatusIsTrue(dto.getCategory())) {
            errors.put("brand", ValidationMessage.CATEGORY_NOT_EXISTS_OR_INACTIVATED);
        }

        if (dto.getBrand() != null && !brandRepository.existsByIdAndStatusIsTrue(dto.getBrand())) {
            errors.put("brand", ValidationMessage.BRAND_NOT_EXISTS_OR_INACTIVATED);
        }

        return errors;
    }

    private String generateProductCode(String brandCode, String categoryCode, Integer id) {
        return String.format("%s-%s-%05d", brandCode, categoryCode, id);
    }
}
