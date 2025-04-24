package com.springboot.user_management.service;

import com.springboot.user_management.dto.request.ProductRequestDTO;
import com.springboot.user_management.dto.request.ProductSearchDTO;
import com.springboot.user_management.dto.response.ProductResponseDTO;
import com.springboot.user_management.dto.response.paging.ProductResponsePagingDTO;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ResponseEntity<BaseResponse<List<ProductResponseDTO>>> findAllProduct();

    ResponseEntity<BaseResponse<ProductResponsePagingDTO>> findAllProductByNameAndStatusAndDate(String name, Boolean status, String startDate, String endDate, Integer page, Integer size);

    ResponseEntity<BaseResponse<ProductResponsePagingDTO>> findAllProductByConditions(ProductSearchDTO dto, Integer page, Integer size);

    ResponseEntity<BaseResponse<ProductResponseDTO>> createProduct(ProductRequestDTO dto);

    ResponseEntity<BaseResponse<ProductResponseDTO>> viewProduct(Integer id);

    ResponseEntity<BaseResponse<ProductResponseDTO>> changeStatus(Integer id, Boolean status);

    ResponseEntity<BaseResponse<ProductResponseDTO>> updateProduct(Integer id, ProductRequestDTO dto);

    ResponseEntity<BaseResponse<ProductResponseDTO>> deleteProducts(List<Integer> ids);

    Map<String, String> validateProduct(ProductRequestDTO dto);
}
