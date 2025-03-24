package com.springboot.user_management.service;

import com.springboot.user_management.entity.Product;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<BaseResponse<List<Product>>> findAllProduct();
}
