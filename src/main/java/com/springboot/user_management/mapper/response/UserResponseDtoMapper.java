package com.springboot.user_management.mapper.response;

import com.springboot.user_management.dto.response.UserResponseDTO;
import com.springboot.user_management.entity.User;
import com.springboot.user_management.mapper.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserResponseDtoMapper extends MapStructMapper<User, UserResponseDTO> {

    @Override
    UserResponseDTO toDTO(User entity);
}
