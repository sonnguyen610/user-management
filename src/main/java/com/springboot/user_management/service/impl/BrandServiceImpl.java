package com.springboot.user_management.service.impl;

import com.springboot.user_management.constant.FailureMessage;
import com.springboot.user_management.constant.SuccessMessage;
import com.springboot.user_management.constant.ValidationMessage;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
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
            dto.trimFields();

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

    @Override
    public Map<String, String> validateBrand(BrandRequestDTO dto) {
        dto.trimFields();
        Map<String, String> errors = new HashMap<>();

        if (dto.getName() != null && brandRepository.existsByName(dto.getName())) {
            errors.put("name", ValidationMessage.NAME_EXISTS);
        }

        if (dto.getCode() != null && brandRepository.existsByCode(dto.getCode())) {
            errors.put("code", ValidationMessage.CODE_EXISTS);
        }

        return errors;
    }
}
