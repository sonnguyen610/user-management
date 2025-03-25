package com.springboot.user_management.service.impl;

import com.springboot.user_management.constant.FailureMessage;
import com.springboot.user_management.constant.SuccessMessage;
import com.springboot.user_management.dto.request.CategoryRequestDTO;
import com.springboot.user_management.entity.Category;
import com.springboot.user_management.repository.CategoryRepository;
import com.springboot.user_management.service.CategoryService;
import com.springboot.user_management.utils.BaseResponse;
import com.springboot.user_management.utils.ResponseFactory;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<BaseResponse<Category>> createCategory(CategoryRequestDTO dto) {
        try {
            if (dto.getName() == null || dto.getName().trim().isEmpty()) {
                throw new BadRequestException(FailureMessage.NOT_BLANK_FIELD);
            }

            if (dto.getCode() == null || dto.getCode().trim().isEmpty()) {
                throw new BadRequestException(FailureMessage.NOT_BLANK_FIELD);
            }

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
}
