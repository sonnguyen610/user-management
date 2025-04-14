package com.springboot.user_management.controller;

import com.springboot.user_management.dto.request.user.UserRegisterRequestDTO;
import com.springboot.user_management.dto.response.user.UserLoginResponseDTO;
import com.springboot.user_management.utils.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public interface AuthController {

    @PostMapping("/register")
    ResponseEntity<BaseResponse<UserLoginResponseDTO>> register(@Valid @RequestBody UserRegisterRequestDTO dto, BindingResult bindingResult);

//    @PostMapping("/login")
//    ResponseEntity<BaseResponse<UserLoginResponseDTO>> login(@RequestBody UserLoginRequestDTO dto, BindingResult bindingResult);
}
