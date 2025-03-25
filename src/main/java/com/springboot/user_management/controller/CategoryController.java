package com.springboot.user_management.controller;

import com.springboot.user_management.dto.request.CategoryRequestDTO;
import com.springboot.user_management.entity.Category;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/categories")
public interface CategoryController {

    @PostMapping
    ResponseEntity<BaseResponse<Category>> createCategory(@RequestBody CategoryRequestDTO dto);
}
