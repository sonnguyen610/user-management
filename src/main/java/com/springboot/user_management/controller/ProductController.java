package com.springboot.user_management.controller;

import com.springboot.user_management.entity.Product;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/products")
public interface ProductController {

    @GetMapping
    ResponseEntity<BaseResponse<List<Product>>> getAllProduct();
}
