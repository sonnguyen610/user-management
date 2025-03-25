package com.springboot.user_management.mapper.response;

import com.springboot.user_management.dto.response.ProductResponseDTO;
import com.springboot.user_management.entity.Brand;
import com.springboot.user_management.entity.Category;
import com.springboot.user_management.entity.Product;
import com.springboot.user_management.mapper.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductResponseDtoMapper extends MapStructMapper<Product, ProductResponseDTO> {

    @Override
    @Mapping(source = "category", target = "category", qualifiedByName = "getCategoryDTO")
    @Mapping(source = "brand", target = "brand", qualifiedByName = "getBrandDTO")
    ProductResponseDTO toDTO(Product entity);

    @Override
    List<ProductResponseDTO> toListDTO(List<Product> entityList);

    @Named("getCategoryDTO")
    default ProductResponseDTO.CategotyDTO getCategoryDTO(Category category) {
        if (category == null) {
            return null;
        }
        return new ProductResponseDTO.CategotyDTO(category.getId(), category.getName());
    }

    @Named("getBrandDTO")
    default ProductResponseDTO.BrandDTO getBrandDTO(Brand brand) {
        if (brand == null) {
            return null;
        }
        return new ProductResponseDTO.BrandDTO(brand.getId(), brand.getName());
    }
}
