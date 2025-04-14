package com.springboot.user_management.controller;

import com.springboot.user_management.dto.request.BrandRequestDTO;
import com.springboot.user_management.dto.response.BrandResponseDTO;
import com.springboot.user_management.dto.response.paging.BrandResponsePagingDTO;
import com.springboot.user_management.utils.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/brands")
public interface BrandController {

    @GetMapping
    ResponseEntity<BaseResponse<List<BrandResponseDTO>>> getAllBrand();

    @GetMapping("/search")
    ResponseEntity<BaseResponse<BrandResponsePagingDTO>> getAllBrandByConditions(@RequestParam(required = false) String name,
                                                                                 @RequestParam(required = false) String createdBy,
                                                                                 @RequestParam(required = false) Boolean status,
                                                                                 @RequestParam(required = true) String date,
                                                                                 @RequestParam(required = false, defaultValue = "1") Integer page,
                                                                                 @RequestParam(required = false, defaultValue = "10") Integer size);

    @PostMapping
    ResponseEntity<BaseResponse<BrandResponseDTO>> createBrand(@Valid @RequestBody BrandRequestDTO dto, BindingResult bindingResult);

    @PutMapping("/{id}/change-status")
    ResponseEntity<BaseResponse<BrandResponseDTO>> changeStatus(@PathVariable Integer id, @RequestParam Boolean status);

    @PutMapping("/{id}")
    ResponseEntity<BaseResponse<BrandResponseDTO>> updateBrand(@PathVariable Integer id, @RequestBody BrandRequestDTO dto, BindingResult bindingResult);

    @DeleteMapping("/{id}")
    ResponseEntity<BaseResponse<BrandResponseDTO>> deleteBrand(@PathVariable Integer id);
}
