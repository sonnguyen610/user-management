package com.springboot.user_management.service;

import com.springboot.user_management.dto.request.CategoryRequestDTO;
import com.springboot.user_management.dto.response.CategoryResponseDTO;
import com.springboot.user_management.entity.Category;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity<BaseResponse<List<CategoryResponseDTO>>> findAllCategory();

    ResponseEntity<BaseResponse<Category>> createCategory(CategoryRequestDTO dto);
}
