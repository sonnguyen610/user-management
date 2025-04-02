package com.springboot.user_management.service.impl;

import com.springboot.user_management.constant.FailureMessage;
import com.springboot.user_management.constant.SuccessMessage;
import com.springboot.user_management.constant.ValidationMessage;
import com.springboot.user_management.dto.request.CategoryRequestDTO;
import com.springboot.user_management.dto.response.CategoryResponseDTO;
import com.springboot.user_management.entity.Category;
import com.springboot.user_management.mapper.response.CategoryResponseDtoMapper;
import com.springboot.user_management.repository.CategoryRepository;
import com.springboot.user_management.service.CategoryService;
import com.springboot.user_management.utils.BaseResponse;
import com.springboot.user_management.utils.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryResponseDtoMapper categoryResponseDtoMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<BaseResponse<List<CategoryResponseDTO>>> findAllCategory() {
        try {
            List<Category> categoryList = categoryRepository.findAll();
            List<CategoryResponseDTO> response = categoryResponseDtoMapper.toListDTO(categoryList);
            return ResponseFactory.success(HttpStatus.OK, response, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<Category>> createCategory(CategoryRequestDTO dto) {
        try {
            dto.trimFields();

            Category category = new Category();
            category.setName(dto.getName());
            category.setCode(dto.getCode());
            category.setDescription(dto.getDescription());
            category.setCreatedBy(dto.getCreatedBy());
            category.setStatus(true);
            categoryRepository.save(category);
            return ResponseFactory.success(HttpStatus.OK, category, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public Map<String, String> validateCategory(CategoryRequestDTO dto) {
        dto.trimFields();
        Map<String, String> errors = new HashMap<>();

        if (dto.getName() != null && categoryRepository.existsByName(dto.getName())) {
            errors.put("name", ValidationMessage.NAME_EXISTS);
        }

        if (dto.getCode() != null && categoryRepository.existsByCode(dto.getCode())) {
            errors.put("code", ValidationMessage.CODE_EXISTS);
        }

        return errors;
    }
}
