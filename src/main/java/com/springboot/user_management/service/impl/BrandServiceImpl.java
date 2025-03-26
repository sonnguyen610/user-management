package com.springboot.user_management.service.impl;

import com.springboot.user_management.constant.FailureMessage;
import com.springboot.user_management.constant.SuccessMessage;
import com.springboot.user_management.dto.request.BrandRequestDTO;
import com.springboot.user_management.dto.response.BrandResponseDTO;
import com.springboot.user_management.entity.Brand;
import com.springboot.user_management.mapper.response.BrandResponseDtoMapper;
import com.springboot.user_management.repository.BrandRepository;
import com.springboot.user_management.service.BrandService;
import com.springboot.user_management.utils.BaseResponse;
import com.springboot.user_management.utils.ResponseFactory;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandResponseDtoMapper brandResponseDtoMapper;

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public ResponseEntity<BaseResponse<List<BrandResponseDTO>>> findAllBrand() {
        try {
            List<Brand> brandList = brandRepository.findAll();
            List<BrandResponseDTO> response = brandResponseDtoMapper.toListDTO(brandList);
            return ResponseFactory.success(HttpStatus.OK, response, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<Brand>> createBrand(BrandRequestDTO dto) {
        try {
            String name = dto.getName() != null ? dto.getName().trim() : null;
            String code = dto.getCode() != null ? dto.getCode().trim() : null;

            if (name == null || name.isEmpty()) {
                throw new BadRequestException(FailureMessage.NOT_BLANK_FIELD);
            }

            if (code == null || code.isEmpty()) {
                throw new BadRequestException(FailureMessage.NOT_BLANK_FIELD);
            }

            if (brandRepository.existsByName(name)) {
                throw new BadRequestException(FailureMessage.NAME_EXISTS);
            }

            if (brandRepository.existsByCode(code)) {
                throw new BadRequestException(FailureMessage.CODE_EXISTS);
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
