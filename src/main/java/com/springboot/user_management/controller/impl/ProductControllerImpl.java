package com.springboot.user_management.controller.impl;

import com.springboot.user_management.controller.ProductController;
import com.springboot.user_management.entity.Product;
import com.springboot.user_management.service.ProductService;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductControllerImpl implements ProductController {
    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<BaseResponse<List<Product>>> getAllProduct() {
        return productService.findAllProduct();
    }
}
