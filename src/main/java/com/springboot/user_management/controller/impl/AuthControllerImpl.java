package com.springboot.user_management.controller.impl;

import com.springboot.user_management.controller.AuthController;
import com.springboot.user_management.dto.request.user.UserRegisterRequestDTO;
import com.springboot.user_management.entity.User;
import com.springboot.user_management.service.AuthService;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {
    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<BaseResponse<User>> register(UserRegisterRequestDTO dto) {
        return authService.register(dto);
    }
}
