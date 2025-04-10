package com.springboot.user_management.service;

import com.springboot.user_management.dto.request.CategoryRequestDTO;
import com.springboot.user_management.dto.response.CategoryResponseDTO;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    ResponseEntity<BaseResponse<List<CategoryResponseDTO>>> findAllCategory();

    ResponseEntity<BaseResponse<CategoryResponseDTO>> createCategory(CategoryRequestDTO dto);

    ResponseEntity<BaseResponse<CategoryResponseDTO>> changeStatus(Integer id, Boolean status);

    ResponseEntity<BaseResponse<CategoryResponseDTO>> updateCategory(Integer id, CategoryRequestDTO dto);

    ResponseEntity<BaseResponse<CategoryResponseDTO>> deleteCategory(Integer id);

    Map<String, String> validateCategory(CategoryRequestDTO dto);
}
