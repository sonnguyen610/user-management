package com.springboot.user_management.mapper.response;

import com.springboot.user_management.dto.response.user.UserResponseDTO;
import com.springboot.user_management.entity.User;
import com.springboot.user_management.mapper.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserResponseDtoMapper extends MapStructMapper<User, UserResponseDTO> {

    @Override
    @Mapping(target = "roles", ignore = true)
    UserResponseDTO toDTO(User entity);

    @Override
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserResponseDTO dto);
}
