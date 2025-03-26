package com.springboot.user_management.service.impl;

import com.springboot.user_management.constant.FailureMessage;
import com.springboot.user_management.constant.SuccessMessage;
import com.springboot.user_management.dto.request.CategoryRequestDTO;
import com.springboot.user_management.dto.response.CategoryResponseDTO;
import com.springboot.user_management.entity.Category;
import com.springboot.user_management.mapper.response.CategoryResponseDtoMapper;
import com.springboot.user_management.repository.CategoryRepository;
import com.springboot.user_management.service.CategoryService;
import com.springboot.user_management.utils.BaseResponse;
import com.springboot.user_management.utils.ResponseFactory;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
            String name = dto.getName() != null ? dto.getName().trim() : null;
            String code = dto.getCode() != null ? dto.getCode().trim() : null;

            if (name == null || name.isEmpty()) {
                throw new BadRequestException(FailureMessage.NOT_BLANK_FIELD);
            }

            if (code == null || code.isEmpty()) {
                throw new BadRequestException(FailureMessage.NOT_BLANK_FIELD);
            }

            if (categoryRepository.existsByName(name)) {
                throw new BadRequestException(FailureMessage.NAME_EXISTS);
            }

            if (categoryRepository.existsByCode(code)) {
                throw new BadRequestException(FailureMessage.CODE_EXISTS);
            }

            Category category = new Category();
            category.setName(name);
            category.setCode(code);
            category.setDescription(dto.getDescription());
            category.setCreatedBy(dto.getCreatedBy());
            category.setStatus(true);
            categoryRepository.save(category);
            return ResponseFactory.success(HttpStatus.OK, category, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }
}
