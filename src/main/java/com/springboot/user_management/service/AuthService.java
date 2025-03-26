package com.springboot.user_management.service;

import com.springboot.user_management.dto.request.user.UserRegisterRequestDTO;
import com.springboot.user_management.entity.User;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<BaseResponse<User>> register(UserRegisterRequestDTO dto);
}
