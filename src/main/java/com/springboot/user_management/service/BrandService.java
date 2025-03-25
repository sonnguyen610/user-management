package com.springboot.user_management.service;

import com.springboot.user_management.dto.request.BrandRequestDTO;
import com.springboot.user_management.entity.Brand;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface BrandService {
    ResponseEntity<BaseResponse<Brand>> createBrand(BrandRequestDTO dto);
}
