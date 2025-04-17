package com.springboot.user_management.controller.impl;

import com.springboot.user_management.constant.ValidationMessage;
import com.springboot.user_management.controller.UserController;
import com.springboot.user_management.dto.request.user.UserInfoRequestDTO;
import com.springboot.user_management.dto.request.user.UserPasswordRequestDTO;
import com.springboot.user_management.dto.response.paging.UserResponsePagingDTO;
import com.springboot.user_management.dto.response.user.UserResponseDTO;
import com.springboot.user_management.exception.ValidationException;
import com.springboot.user_management.service.UserService;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserControllerImpl implements UserController {
    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<BaseResponse<UserResponseDTO>> getUserInfo() {
        return userService.getUserInfo();
    }

    @Override
    public ResponseEntity<BaseResponse<UserResponseDTO>> changeUserPassword(UserPasswordRequestDTO dto, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors, ValidationMessage.VALIDATION_FAILED);
        }

        return userService.changeUserPassword(dto);
    }

    @Override
    public ResponseEntity<BaseResponse<UserResponseDTO>> updateUserInfo(UserInfoRequestDTO dto, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors, ValidationMessage.VALIDATION_FAILED);
        }

        return userService.updateUserInfo(dto);
    }

    @Override
    public ResponseEntity<BaseResponse<UserResponsePagingDTO>> getAllUserByConditions(String startDate, String endDate, String username, String fullName, Boolean status, String role, Integer page, Integer size) {
        return userService.getAllUserByConditions(startDate, endDate, username, fullName, status, role, page, size);
    }

    @Override
    public ResponseEntity<BaseResponse<UserResponseDTO>> updateUserStatus(Integer id, Boolean status) {
        return userService.updateUserStatus(id, status);
    }

    @Override
    public ResponseEntity<BaseResponse<UserResponseDTO>> updateUserRole(Integer id, String role) {
        return userService.updateUserRole(id, role);
    }
}
