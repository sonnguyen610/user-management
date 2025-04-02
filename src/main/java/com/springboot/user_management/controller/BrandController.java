package com.springboot.user_management.controller;

import com.springboot.user_management.dto.request.BrandRequestDTO;
import com.springboot.user_management.dto.response.BrandResponseDTO;
import com.springboot.user_management.entity.Brand;
import com.springboot.user_management.utils.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/brands")
public interface BrandController {

    @GetMapping
    ResponseEntity<BaseResponse<List<BrandResponseDTO>>> getAllBrand();

    @PostMapping
    ResponseEntity<BaseResponse<Brand>> createBrand(@Valid @RequestBody BrandRequestDTO dto, BindingResult bindingResult);
}
