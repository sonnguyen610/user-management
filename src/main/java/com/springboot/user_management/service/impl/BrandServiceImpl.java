package com.springboot.user_management.service.impl;

import com.springboot.user_management.constant.FailureMessage;
import com.springboot.user_management.constant.SuccessMessage;
import com.springboot.user_management.dto.request.BrandRequestDTO;
import com.springboot.user_management.entity.Brand;
import com.springboot.user_management.entity.Category;
import com.springboot.user_management.repository.BrandRepository;
import com.springboot.user_management.service.BrandService;
import com.springboot.user_management.utils.BaseResponse;
import com.springboot.user_management.utils.ResponseFactory;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public ResponseEntity<BaseResponse<Brand>> createBrand(BrandRequestDTO dto) {
        try {
            if (dto.getName() == null || dto.getName().trim().isEmpty()) {
                throw new BadRequestException(FailureMessage.NOT_BLANK_FIELD);
            }

            if (dto.getCode() == null || dto.getCode().trim().isEmpty()) {
                throw new BadRequestException(FailureMessage.NOT_BLANK_FIELD);
            }

            Brand brand = new Brand();
            brand.setName(dto.getName());
            brand.setCode(dto.getCode());
            brand.setDescription(dto.getDescription());
            brand.setCreatedBy(dto.getCreatedBy());
            brand.setStatus(true);
            brandRepository.save(brand);
            return ResponseFactory.success(HttpStatus.OK, brand, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }
}
