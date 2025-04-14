package com.springboot.user_management.service.impl;

import com.springboot.user_management.constant.FailureMessage;
import com.springboot.user_management.constant.SuccessMessage;
import com.springboot.user_management.constant.ValidationMessage;
import com.springboot.user_management.dto.request.CategoryRequestDTO;
import com.springboot.user_management.dto.response.BrandResponseDTO;
import com.springboot.user_management.dto.response.CategoryResponseDTO;
import com.springboot.user_management.dto.response.paging.BrandResponsePagingDTO;
import com.springboot.user_management.dto.response.paging.CategoryResponsePagingDTO;
import com.springboot.user_management.dto.response.paging.Metadata;
import com.springboot.user_management.dto.response.paging.ProductResponsePagingDTO;
import com.springboot.user_management.entity.Brand;
import com.springboot.user_management.entity.Category;
import com.springboot.user_management.entity.Product;
import com.springboot.user_management.mapper.response.CategoryResponseDtoMapper;
import com.springboot.user_management.repository.CategoryRepository;
import com.springboot.user_management.repository.ProductRepository;
import com.springboot.user_management.service.CategoryService;
import com.springboot.user_management.utils.BaseResponse;
import com.springboot.user_management.utils.ResponseFactory;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryResponseDtoMapper categoryResponseDtoMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

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
    public ResponseEntity<BaseResponse<CategoryResponsePagingDTO>> getAllCategoryByConditions(String name, String createdBy, Boolean status, String date, Integer page, Integer size) {
        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Category> categoryPage = categoryRepository.findAllByConditions(name, createdBy, status, date, pageable);
            List<CategoryResponseDTO> responseDTOList = categoryPage.getContent().stream().map(categoryResponseDtoMapper::toDTO).collect(Collectors.toList());
            CategoryResponsePagingDTO responsePagingDTO = new CategoryResponsePagingDTO(Metadata.build(categoryPage), responseDTOList);
            return ResponseFactory.success(HttpStatus.OK, responsePagingDTO, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, FailureMessage.FAILURE);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<CategoryResponseDTO>> createCategory(CategoryRequestDTO dto) {
        try {
            dto.trimFields();

            Category category = new Category();
            category.setName(dto.getName());
            category.setCode(dto.getCode());
            category.setDescription(dto.getDescription());
            category.setCreatedBy(dto.getCreatedBy());
            categoryRepository.save(category);
            CategoryResponseDTO responseDTO = categoryResponseDtoMapper.toDTO(category);
            return ResponseFactory.success(HttpStatus.OK, responseDTO, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<CategoryResponseDTO>> changeStatus(Integer id, Boolean status) {
        try {
            if (!categoryRepository.existsById(id)) {
                throw new BadRequestException(FailureMessage.DATA_NOT_FOUND);
            }

            Category category = categoryRepository.getReferenceById(id);
            List<Product> productList = category.getProducts();

            if (!productList.isEmpty()) {
                if (status) {
                    productList.forEach(product -> product.setStatus(product.getOriginalStatus()));
                } else {
                    productList.forEach(product -> product.setOriginalStatus(product.getStatus()));
                }
            }

            categoryRepository.save(category);
            productRepository.saveAll(productList);
            CategoryResponseDTO responseDTO = categoryResponseDtoMapper.toDTO(category);
            return ResponseFactory.success(HttpStatus.OK, responseDTO, SuccessMessage.STATUS_CHANGED);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<CategoryResponseDTO>> updateCategory(Integer id, CategoryRequestDTO dto) {
        try {
            if (!categoryRepository.existsById(id)) {
                throw new BadRequestException(FailureMessage.DATA_NOT_FOUND);
            }

            if (categoryRepository.existsByNameAndIdNot(dto.getName(), id)) {
                throw new BadRequestException(ValidationMessage.NAME_EXISTS);
            }

            Category category = categoryRepository.getReferenceById(id);
            List<Product> productList = category.getProducts();
            if (!category.getStatus()) {
                throw new BadRequestException(FailureMessage.PRODUCT_INACTIVATED);
            }

            category.setName(dto.getName());
            category.setDescription(dto.getDescription());
            if (!Objects.equals(category.getCode(), dto.getCode())) {
                if (!productList.isEmpty()) {
                    productList.forEach(product ->
                            product.setCode(product.getCode().replace(category.getCode(), dto.getCode()))
                    );
                }

                category.setCode(dto.getCode());
            }
            productRepository.saveAll(productList);
            categoryRepository.save(category);
            CategoryResponseDTO responseDTO = categoryResponseDtoMapper.toDTO(category);
            return ResponseFactory.success(HttpStatus.OK, responseDTO, SuccessMessage.DATA_UPDATED);
        } catch (Exception e) {
            return ResponseFactory.success(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<CategoryResponseDTO>> deleteCategory(Integer id) {
        try {
            if (!categoryRepository.existsById(id)) {
                throw new BadRequestException(FailureMessage.DATA_NOT_FOUND);
            }

            Category category = categoryRepository.getReferenceById(id);
            List<Product> productList = category.getProducts();
            productRepository.deleteAll(productList);
            categoryRepository.delete(category);
            return ResponseFactory.success(HttpStatus.OK, null, SuccessMessage.DELETE_SUCCESS);
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
