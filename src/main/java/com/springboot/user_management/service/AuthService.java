package com.springboot.user_management.service;

import com.springboot.user_management.dto.request.user.UserLoginRequestDTO;
import com.springboot.user_management.dto.request.user.UserRegisterRequestDTO;
import com.springboot.user_management.dto.response.user.UserLoginResponseDTO;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {
    ResponseEntity<BaseResponse<UserLoginResponseDTO>> register(UserRegisterRequestDTO dto);

    ResponseEntity<BaseResponse<UserLoginResponseDTO>> login(UserLoginRequestDTO dto);

    Map<String, String> validateUserRegister(UserRegisterRequestDTO dto);
}
