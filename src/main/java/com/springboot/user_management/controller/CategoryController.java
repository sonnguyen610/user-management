package com.springboot.user_management.controller;

import com.springboot.user_management.dto.request.CategoryRequestDTO;
import com.springboot.user_management.dto.response.CategoryResponseDTO;
import com.springboot.user_management.dto.response.paging.CategoryResponsePagingDTO;
import com.springboot.user_management.utils.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/categories")
public interface CategoryController {

    @GetMapping
    ResponseEntity<BaseResponse<List<CategoryResponseDTO>>> getAllCategory();

    @GetMapping("/search")
    ResponseEntity<BaseResponse<CategoryResponsePagingDTO>> getAllCategoryByConditions(@RequestParam(required = false) String name,
                                                                                             @RequestParam(required = false) String createdBy,
                                                                                             @RequestParam(required = false) Boolean status,
                                                                                             @RequestParam(required = true) String date,
                                                                                             @RequestParam(required = false, defaultValue = "1") Integer page,
                                                                                             @RequestParam(required = false, defaultValue = "10") Integer size);

    @PostMapping
    ResponseEntity<BaseResponse<CategoryResponseDTO>> createCategory(@Valid @RequestBody CategoryRequestDTO dto, BindingResult bindingResult);

    @PutMapping("/{id}/change-status")
    ResponseEntity<BaseResponse<CategoryResponseDTO>> changeStatus(@PathVariable Integer id, @RequestParam Boolean status);

    @PutMapping("/{id}")
    ResponseEntity<BaseResponse<CategoryResponseDTO>> updateCategory(@PathVariable Integer id, @RequestBody CategoryRequestDTO dto, BindingResult bindingResult);

    @DeleteMapping("/{id}")
    ResponseEntity<BaseResponse<CategoryResponseDTO>> deleteCategory(@PathVariable Integer id);
}
