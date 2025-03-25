package com.springboot.user_management.mapper.response;

import com.springboot.user_management.dto.response.ProductResponseDTO;
import com.springboot.user_management.entity.Product;
import com.springboot.user_management.mapper.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductResponseDtoMapper extends MapStructMapper<Product, ProductResponseDTO> {

    @Override
    List<ProductResponseDTO> toListDTO(List<Product> entityList);
}
