package com.springboot.user_management.controller.impl;

import com.springboot.user_management.constant.ValidationMessage;
import com.springboot.user_management.controller.CategoryController;
import com.springboot.user_management.dto.request.CategoryRequestDTO;
import com.springboot.user_management.dto.response.CategoryResponseDTO;
import com.springboot.user_management.exception.ValidationException;
import com.springboot.user_management.service.CategoryService;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryControllerImpl implements CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseEntity<BaseResponse<List<CategoryResponseDTO>>> getAllCategory() {
        return categoryService.findAllCategory();
    }

    @Override
    public ResponseEntity<BaseResponse<CategoryResponseDTO>> createCategory(CategoryRequestDTO dto, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
        }

        errors.putAll(categoryService.validateCategory(dto));

        if (!errors.isEmpty()) {
            throw new ValidationException(errors, ValidationMessage.VALIDATION_FAILED);
        }

        return categoryService.createCategory(dto);
    }

    @Override
    public ResponseEntity<BaseResponse<CategoryResponseDTO>> changeStatus(Integer id, Boolean status) {
        return categoryService.changeStatus(id, status);
    }

    @Override
    public ResponseEntity<BaseResponse<CategoryResponseDTO>> updateCategory(Integer id, CategoryRequestDTO dto, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
        }

        errors.putAll(categoryService.validateCategory(dto));

        if (!errors.isEmpty()) {
            throw new ValidationException(errors, ValidationMessage.VALIDATION_FAILED);
        }

        return categoryService.updateCategory(id, dto);
    }

    @Override
    public ResponseEntity<BaseResponse<CategoryResponseDTO>> deleteCategory(Integer id) {
        return categoryService.deleteCategory(id);
    }
}
