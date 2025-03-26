package com.springboot.user_management.service;

import com.springboot.user_management.dto.request.BrandRequestDTO;
import com.springboot.user_management.dto.response.BrandResponseDTO;
import com.springboot.user_management.entity.Brand;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BrandService {
    ResponseEntity<BaseResponse<List<BrandResponseDTO>>> findAllBrand();

    ResponseEntity<BaseResponse<Brand>> createBrand(BrandRequestDTO dto);
}
