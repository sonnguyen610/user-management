package com.springboot.user_management.service.impl;

import com.springboot.user_management.constant.FailureMessage;
import com.springboot.user_management.constant.SuccessMessage;
import com.springboot.user_management.dto.request.ProductRequestDTO;
import com.springboot.user_management.dto.response.ProductResponseDTO;
import com.springboot.user_management.entity.Brand;
import com.springboot.user_management.entity.Category;
import com.springboot.user_management.entity.Product;
import com.springboot.user_management.mapper.response.ProductResponseDtoMapper;
import com.springboot.user_management.repository.BrandRepository;
import com.springboot.user_management.repository.CategoryRepository;
import com.springboot.user_management.repository.ProductRepository;
import com.springboot.user_management.service.ProductService;
import com.springboot.user_management.utils.BaseResponse;
import com.springboot.user_management.utils.ResponseFactory;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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

    @Override
    public ResponseEntity<BaseResponse<List<ProductResponseDTO>>> findAllProduct() {
        try {
            List<Product> productList = productRepository.findAllByStatusIsTrue();
            List<ProductResponseDTO> response = productResponseDtoMapper.toListDTO(productList);
            return ResponseFactory.success(HttpStatus.OK, response, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, FailureMessage.FAILURE);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<ProductResponseDTO>> createProduct(ProductRequestDTO dto) {
        try {
            String name = dto.getName() != null ? dto.getName().trim() : null;

            if (name == null || name.isEmpty()) {
                throw new BadRequestException(FailureMessage.NOT_BLANK_FIELD);
            }

            if (dto.getPrice() == null || dto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new BadRequestException(FailureMessage.PRODUCT_PRICE_ERROR);
            }

            if (dto.getCategoryId() == null || !categoryRepository.existsByIdAndStatusIsTrue(dto.getCategoryId())) {
                throw new BadRequestException(FailureMessage.CATEGORY_NOT_SELECTED);
            }

            if (dto.getBrandId() == null || !brandRepository.existsByIdAndStatusIsTrue(dto.getBrandId())) {
                throw new BadRequestException(FailureMessage.BRAND_NOT_SELECTED);
            }

            if (productRepository.existsByName(name)) {
                throw new BadRequestException(FailureMessage.NAME_EXISTS);
            }

            Product product = new Product();
            product.setName(name);
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            product.setQuantity(dto.getQuantity());
            product.setStatus(true);

            Category category = categoryRepository.getReferenceById(dto.getCategoryId());
            product.setCategory(category);

            Brand brand = brandRepository.getReferenceById(dto.getBrandId());
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
    public ResponseEntity<BaseResponse<ProductResponseDTO>> editProduct(Integer id, ProductRequestDTO dto) {
        try {
            String name = dto.getName() != null ? dto.getName().trim() : null;

            if (!productRepository.existsById(id)) {
                throw new BadRequestException(FailureMessage.PRODUCT_NOT_FOUND);
            }

            if (name == null || name.isEmpty()) {
                throw new BadRequestException(FailureMessage.NOT_BLANK_FIELD);
            }

            if (productRepository.existsByNameAndIdNot(name, id)) {
                throw new BadRequestException(FailureMessage.NAME_EXISTS);
            }

            if (dto.getPrice() == null || dto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new BadRequestException(FailureMessage.PRODUCT_PRICE_ERROR);
            }

            Product product = productRepository.getReferenceById(id);
            product.setName(name);
            product.setPrice(dto.getPrice());
            product.setQuantity(dto.getQuantity() == null ? product.getQuantity() : dto.getQuantity());
            product.setDescription(dto.getDescription() == null ? product.getDescription() : dto.getDescription());

            if (dto.getCategoryId() != null) {
                if (!categoryRepository.existsByIdAndStatusIsTrue(dto.getCategoryId())) {
                    throw new BadRequestException(FailureMessage.CATEGORY_NOT_FOUND);
                }

                Category category = categoryRepository.getReferenceById(dto.getCategoryId());
                product.setCategory(category);
            }

            if (dto.getBrandId() != null) {
                if (!brandRepository.existsByIdAndStatusIsTrue(dto.getBrandId())) {
                    throw new BadRequestException(FailureMessage.BRAND_NOT_FOUND);
                }

                Brand brand = brandRepository.getReferenceById(dto.getBrandId());
                product.setBrand(brand);
            }

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

    private String generateProductCode(String brandCode, String categoryCode, Integer id) {
        return String.format("%s-%s-%05d", brandCode, categoryCode, id);
    }
}
