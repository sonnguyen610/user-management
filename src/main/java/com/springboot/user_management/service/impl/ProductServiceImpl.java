package com.springboot.user_management.service.impl;

import com.springboot.user_management.constant.FailureMessage;
import com.springboot.user_management.constant.SuccessMessage;
import com.springboot.user_management.constant.ValidationMessage;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            List<Product> productList = productRepository.findAll();
            List<ProductResponseDTO> response = productResponseDtoMapper.toListDTO(productList);
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
            product.setStatus(true);

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
    public Map<String, String> validateProduct(ProductRequestDTO dto) {
        dto.trimFields();
        Map<String, String> errors = new HashMap<>();

        if (dto.getName() != null && productRepository.existsByName(dto.getName())) {
            errors.put("name", ValidationMessage.NAME_EXISTS);
        }

        if (dto.getCategory() != null && !categoryRepository.existsByIdAndStatusIsTrue(dto.getCategory())) {
            errors.put("brand", ValidationMessage.CATEGORY_NOT_EXISTS);
        }

        if (dto.getBrand() != null && !brandRepository.existsByIdAndStatusIsTrue(dto.getBrand())) {
            errors.put("brand", ValidationMessage.BRAND_NOT_EXISTS);
        }

        return errors;
    }

    private String generateProductCode(String brandCode, String categoryCode, Integer id) {
        return String.format("%s-%s-%05d", brandCode, categoryCode, id);
    }
}
