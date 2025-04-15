package com.springboot.user_management.mapper.response;

import com.springboot.user_management.dto.response.user.UserLoginResponseDTO;
import com.springboot.user_management.entity.Role;
import com.springboot.user_management.entity.User;
import com.springboot.user_management.mapper.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserLoginResponseDtoMapper extends MapStructMapper<User, UserLoginResponseDTO> {

    @Override
    @Mapping(target = "roles", ignore = true)
    UserLoginResponseDTO toDTO(User entity);

    @Override
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserLoginResponseDTO dto);
}
