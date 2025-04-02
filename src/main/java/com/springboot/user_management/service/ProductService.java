package com.springboot.user_management.service;

import com.springboot.user_management.dto.request.ProductRequestDTO;
import com.springboot.user_management.dto.response.ProductResponseDTO;
import com.springboot.user_management.entity.Product;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ResponseEntity<BaseResponse<List<ProductResponseDTO>>> findAllProduct();

    ResponseEntity<BaseResponse<ProductResponseDTO>> createProduct(ProductRequestDTO dto);

    ResponseEntity<BaseResponse<ProductResponseDTO>> viewProduct(Integer id);

    Map<String, String> validateProduct(ProductRequestDTO dto);
}
