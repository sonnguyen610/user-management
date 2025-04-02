package com.springboot.user_management.controller.impl;

import com.springboot.user_management.constant.ValidationMessage;
import com.springboot.user_management.controller.BrandController;
import com.springboot.user_management.dto.request.BrandRequestDTO;
import com.springboot.user_management.dto.response.BrandResponseDTO;
import com.springboot.user_management.entity.Brand;
import com.springboot.user_management.exception.ValidationException;
import com.springboot.user_management.service.BrandService;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BrandControllerImpl implements BrandController {
    @Autowired
    private BrandService brandService;

    @Override
    public ResponseEntity<BaseResponse<List<BrandResponseDTO>>> getAllBrand() {
        return brandService.findAllBrand();
    }

    @Override
    public ResponseEntity<BaseResponse<Brand>> createBrand(BrandRequestDTO dto, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
        }

        errors.putAll(brandService.validateBrand(dto));

        if (!errors.isEmpty()) {
            throw new ValidationException(errors, ValidationMessage.VALIDATION_FAILED);
        }

        return brandService.createBrand(dto);
    }
}
