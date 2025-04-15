package com.springboot.user_management.controller;

import com.springboot.user_management.dto.request.ProductRequestDTO;
import com.springboot.user_management.dto.response.ProductResponseDTO;
import com.springboot.user_management.dto.response.paging.ProductResponsePagingDTO;
import com.springboot.user_management.entity.Product;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/products")
public interface ProductController {

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping
    ResponseEntity<BaseResponse<List<ProductResponseDTO>>> getAllProduct();

    @GetMapping("/search")
    ResponseEntity<BaseResponse<ProductResponsePagingDTO>> getAllProductByConditions(@RequestParam(required = false) String name,
                                                                                     @RequestParam(required = false) String createdBy,
                                                                                     @RequestParam(required = false) Boolean status,
                                                                                     @RequestParam(required = true) String date,
                                                                                     @RequestParam(required = false, defaultValue = "1") Integer page,
                                                                                     @RequestParam(required = false, defaultValue = "10") Integer size);

    @PostMapping
    ResponseEntity<BaseResponse<ProductResponseDTO>> createProduct(@RequestBody ProductRequestDTO dto, BindingResult bindingResult);

    @GetMapping("/{id}")
    ResponseEntity<BaseResponse<ProductResponseDTO>> viewProduct(@PathVariable Integer id);

    @PutMapping("/{id}/change-status")
    ResponseEntity<BaseResponse<ProductResponseDTO>> changeStatus(@PathVariable Integer id, @RequestParam Boolean status);

    @PutMapping("/{id}")
    ResponseEntity<BaseResponse<ProductResponseDTO>> updateProduct(@PathVariable Integer id, @RequestBody ProductRequestDTO dto, BindingResult bindingResult);

    @DeleteMapping
    ResponseEntity<BaseResponse<ProductResponseDTO>> deleteProduct(@RequestParam List<Integer> ids);
}
