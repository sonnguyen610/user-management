package com.springboot.user_management.controller.impl;

import com.springboot.user_management.controller.CategoryController;
import com.springboot.user_management.dto.request.CategoryRequestDTO;
import com.springboot.user_management.entity.Category;
import com.springboot.user_management.service.CategoryService;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryControllerImpl implements CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseEntity<BaseResponse<Category>> createCategory(CategoryRequestDTO dto) {
        return categoryService.createCategory(dto);
    }
}
