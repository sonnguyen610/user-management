package com.springboot.user_management.mapper.response;

import com.springboot.user_management.dto.response.ProductResponseForParentDTO;
import com.springboot.user_management.entity.Product;
import com.springboot.user_management.mapper.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductResponseForParentDtoMapper extends MapStructMapper<Product, ProductResponseForParentDTO> {

    @Override
    ProductResponseForParentDTO toDTO(Product entity);

    @Override
    List<ProductResponseForParentDTO> toListDTO(List<Product> entityList);
}
