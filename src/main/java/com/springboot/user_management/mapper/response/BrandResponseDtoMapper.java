package com.springboot.user_management.mapper.response;

import com.springboot.user_management.dto.response.BrandResponseDTO;
import com.springboot.user_management.entity.Brand;
import com.springboot.user_management.mapper.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandResponseDtoMapper extends MapStructMapper<Brand, BrandResponseDTO> {

    @Override
    BrandResponseDTO toDTO(Brand entity);

    @Override
    List<BrandResponseDTO> toListDTO(List<Brand> entityList);
}
