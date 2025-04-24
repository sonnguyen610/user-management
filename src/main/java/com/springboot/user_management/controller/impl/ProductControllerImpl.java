package com.springboot.user_management.controller.impl;

import com.springboot.user_management.constant.ValidationMessage;
import com.springboot.user_management.controller.ProductController;
import com.springboot.user_management.dto.request.ProductRequestDTO;
import com.springboot.user_management.dto.request.ProductSearchDTO;
import com.springboot.user_management.dto.response.ProductResponseDTO;
import com.springboot.user_management.dto.response.paging.ProductResponsePagingDTO;
import com.springboot.user_management.entity.Product;
import com.springboot.user_management.exception.ValidationException;
import com.springboot.user_management.service.ProductService;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductControllerImpl implements ProductController {
    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<BaseResponse<List<ProductResponseDTO>>> getAllProduct() {
        return productService.findAllProduct();
    }

    @Override
    public ResponseEntity<BaseResponse<ProductResponsePagingDTO>> getAllProductByOwner(String name, Boolean status, String startDate, String endDate, Integer page, Integer size) {
        return productService.findAllProductByNameAndStatusAndDate(name, status, startDate, endDate, page, size);
    }

    @Override
    public ResponseEntity<BaseResponse<ProductResponsePagingDTO>> getAllProductByConditions(ProductSearchDTO dto, Integer page, Integer size) {
        return productService.findAllProductByConditions(dto, page, size);
    }

    @Override
    public ResponseEntity<BaseResponse<ProductResponseDTO>> createProduct(ProductRequestDTO dto, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
        }

        errors.putAll(productService.validateProduct(dto));

        if (!errors.isEmpty()) {
            throw new ValidationException(errors, ValidationMessage.VALIDATION_FAILED);
        }

        return productService.createProduct(dto);
    }


    @Override
    public ResponseEntity<BaseResponse<ProductResponseDTO>> viewProduct(Integer id) {
        return productService.viewProduct(id);
    }

    @Override
    public ResponseEntity<BaseResponse<ProductResponseDTO>> changeStatus(Integer id, Boolean status) {
        return productService.changeStatus(id, status);
    }

    @Override
    public ResponseEntity<BaseResponse<ProductResponseDTO>> updateProduct(Integer id, ProductRequestDTO dto, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
        }

        errors.putAll(productService.validateProduct(dto));

        if (!errors.isEmpty()) {
            throw new ValidationException(errors, ValidationMessage.VALIDATION_FAILED);
        }

        return productService.updateProduct(id, dto);
    }

    @Override
    public ResponseEntity<BaseResponse<ProductResponseDTO>> deleteProduct(List<Integer> ids) {
        return productService.deleteProducts(ids);
    }
}
