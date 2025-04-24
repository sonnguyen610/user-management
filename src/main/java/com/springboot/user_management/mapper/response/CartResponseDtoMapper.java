package com.springboot.user_management.mapper.response;

import com.springboot.user_management.dto.response.CartResponseDTO;
import com.springboot.user_management.entity.Cart;
import com.springboot.user_management.mapper.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartResponseDtoMapper extends MapStructMapper<Cart, CartResponseDTO> {

    @Override
    Cart toEntity(CartResponseDTO dto);

    @Override
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "outOfStock", ignore = true)
    CartResponseDTO toDTO(Cart entity);

    @Override
    List<CartResponseDTO> toListDTO(List<Cart> entityList);
}
