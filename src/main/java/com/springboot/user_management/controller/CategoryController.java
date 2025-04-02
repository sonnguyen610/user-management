package com.springboot.user_management.controller;

import com.springboot.user_management.dto.request.CategoryRequestDTO;
import com.springboot.user_management.dto.response.CategoryResponseDTO;
import com.springboot.user_management.entity.Category;
import com.springboot.user_management.utils.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/categories")
public interface CategoryController {

    @GetMapping
    ResponseEntity<BaseResponse<List<CategoryResponseDTO>>> getAllCategory();

    @PostMapping
    ResponseEntity<BaseResponse<Category>> createCategory(@Valid @RequestBody CategoryRequestDTO dto, BindingResult bindingResult);
}
