package com.springboot.user_management.mapper.response;

import com.springboot.user_management.dto.response.CategoryResponseDTO;
import com.springboot.user_management.entity.Category;
import com.springboot.user_management.mapper.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryResponseDtoMapper extends MapStructMapper<Category, CategoryResponseDTO> {

    @Override
    CategoryResponseDTO toDTO(Category entity);

    @Override
    List<CategoryResponseDTO> toListDTO(List<Category> entityList);
}
