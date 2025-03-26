package com.springboot.user_management.controller.impl;

import com.springboot.user_management.controller.BrandController;
import com.springboot.user_management.dto.request.BrandRequestDTO;
import com.springboot.user_management.dto.response.BrandResponseDTO;
import com.springboot.user_management.entity.Brand;
import com.springboot.user_management.service.BrandService;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BrandControllerImpl implements BrandController {
    @Autowired
    private BrandService brandService;

    @Override
    public ResponseEntity<BaseResponse<List<BrandResponseDTO>>> getAllBrand() {
        return brandService.findAllBrand();
    }

    @Override
    public ResponseEntity<BaseResponse<Brand>> createBrand(BrandRequestDTO dto) {
        return brandService.createBrand(dto);
    }
}
