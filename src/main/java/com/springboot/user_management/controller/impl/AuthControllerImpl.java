package com.springboot.user_management.controller.impl;

import com.springboot.user_management.constant.ValidationMessage;
import com.springboot.user_management.controller.AuthController;
import com.springboot.user_management.dto.request.user.UserRegisterRequestDTO;
import com.springboot.user_management.dto.response.user.UserLoginResponseDTO;
import com.springboot.user_management.exception.ValidationException;
import com.springboot.user_management.service.AuthService;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthControllerImpl implements AuthController {
    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<BaseResponse<UserLoginResponseDTO>> register(UserRegisterRequestDTO dto, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
        }

        errors.putAll(authService.validateUserRegister(dto));

        if (!errors.isEmpty()) {
            throw new ValidationException(errors, ValidationMessage.VALIDATION_FAILED);
        }

        return authService.register(dto);
    }

//    @Override
//    public ResponseEntity<BaseResponse<UserLoginResponseDTO>> login(UserLoginRequestDTO dto, BindingResult bindingResult) {
//        Map<String, String> errors = new HashMap<>();
//
//        if (bindingResult.hasErrors()) {
//            bindingResult.getFieldErrors().forEach(error -> {
//                errors.put(error.getField(), error.getDefaultMessage());
//            });
//        }
//
//        if (!errors.isEmpty()) {
//            throw new ValidationException(errors, ValidationMessage.VALIDATION_FAILED);
//        }
//
//        return authService.login(dto);
//    }
}
