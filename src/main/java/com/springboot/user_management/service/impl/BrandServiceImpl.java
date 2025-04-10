package com.springboot.user_management.service.impl;

import com.springboot.user_management.constant.FailureMessage;
import com.springboot.user_management.constant.SuccessMessage;
import com.springboot.user_management.constant.ValidationMessage;
import com.springboot.user_management.dto.request.BrandRequestDTO;
import com.springboot.user_management.dto.response.BrandResponseDTO;
import com.springboot.user_management.entity.Brand;
import com.springboot.user_management.entity.Product;
import com.springboot.user_management.mapper.response.BrandResponseDtoMapper;
import com.springboot.user_management.repository.BrandRepository;
import com.springboot.user_management.repository.ProductRepository;
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
import java.util.Objects;

@Service
@Transactional(rollbackFor = Exception.class)
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandResponseDtoMapper brandResponseDtoMapper;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

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
    public ResponseEntity<BaseResponse<BrandResponseDTO>> createBrand(BrandRequestDTO dto) {
        try {
            dto.trimFields();

            Brand brand = new Brand();
            brand.setName(dto.getName());
            brand.setCode(dto.getCode());
            brand.setDescription(dto.getDescription());
            brand.setCreatedBy(dto.getCreatedBy());
            brand.setStatus(true);
            brandRepository.save(brand);
            BrandResponseDTO responseDTO = brandResponseDtoMapper.toDTO(brand);
            return ResponseFactory.success(HttpStatus.OK, responseDTO, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<BrandResponseDTO>> changeStatus(Integer id, Boolean status) {
        try {
            if (!brandRepository.existsById(id)) {
                throw new BadRequestException(FailureMessage.DATA_NOT_FOUND);
            }

            Brand brand = brandRepository.getReferenceById(id);
            List<Product> productList = brand.getProducts();

            if (status) {
                if (!productList.isEmpty()) {
                    productList.forEach(product -> product.setStatus(product.getOriginalStatus()));
                }
            } else {
                if (!productList.isEmpty()) {
                    productList.forEach(product -> product.setOriginalStatus(product.getStatus()));
                }
            }

            brandRepository.save(brand);
            productRepository.saveAll(productList);
            BrandResponseDTO responseDTO = brandResponseDtoMapper.toDTO(brand);
            return ResponseFactory.success(HttpStatus.OK, responseDTO, SuccessMessage.STATUS_CHANGED);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<BrandResponseDTO>> updateBrand(Integer id, BrandRequestDTO dto) {
        try {
            if (!brandRepository.existsById(id)) {
                throw new BadRequestException(FailureMessage.DATA_NOT_FOUND);
            }

            if (brandRepository.existsByNameAndIdNot(dto.getName(), id)) {
                throw new BadRequestException(ValidationMessage.NAME_EXISTS);
            }

            Brand brand = brandRepository.getReferenceById(id);
            List<Product> productList = brand.getProducts();
            if (!brand.getStatus()) {
                throw new BadRequestException(FailureMessage.PRODUCT_INACTIVATED);
            }

            brand.setName(dto.getName());
            brand.setDescription(dto.getDescription());
            if (!Objects.equals(brand.getCode(), dto.getCode())) {
                if (!productList.isEmpty()) {
                    productList.forEach(product ->
                            product.setCode(product.getCode().replace(brand.getCode(), dto.getCode()))
                    );
                }

                brand.setCode(dto.getCode());
            }
            productRepository.saveAll(productList);
            brandRepository.save(brand);
            BrandResponseDTO responseDTO = brandResponseDtoMapper.toDTO(brand);
            return ResponseFactory.success(HttpStatus.OK, responseDTO, SuccessMessage.DATA_UPDATED);
        } catch (Exception e) {
            return ResponseFactory.success(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<BrandResponseDTO>> deleteBrand(Integer id) {
        try {
            if (!brandRepository.existsById(id)) {
                throw new BadRequestException(FailureMessage.DATA_NOT_FOUND);
            }

            Brand brand = brandRepository.getReferenceById(id);
            List<Product> productList = brand.getProducts();
            productRepository.deleteAll(productList);
            brandRepository.delete(brand);
            return ResponseFactory.success(HttpStatus.OK, null, SuccessMessage.DELETE_SUCCESS);
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
