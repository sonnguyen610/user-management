package com.springboot.user_management.service;

import com.springboot.user_management.dto.request.BrandRequestDTO;
import com.springboot.user_management.dto.response.BrandResponseDTO;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BrandService {
    ResponseEntity<BaseResponse<List<BrandResponseDTO>>> findAllBrand();

    ResponseEntity<BaseResponse<BrandResponseDTO>> createBrand(BrandRequestDTO dto);

    ResponseEntity<BaseResponse<BrandResponseDTO>> changeStatus(Integer id, Boolean status);

    ResponseEntity<BaseResponse<BrandResponseDTO>> updateBrand(Integer id, BrandRequestDTO dto);

    ResponseEntity<BaseResponse<BrandResponseDTO>> deleteBrand(Integer id);

    Map<String, String> validateBrand(BrandRequestDTO dto);
}
