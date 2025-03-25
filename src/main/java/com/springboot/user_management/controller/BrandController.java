package com.springboot.user_management.controller;

import com.springboot.user_management.dto.request.BrandRequestDTO;
import com.springboot.user_management.entity.Brand;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/brands")
public interface BrandController {

    @PostMapping
    ResponseEntity<BaseResponse<Brand>> createBrand(@RequestBody BrandRequestDTO dto);
}
