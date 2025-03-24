package com.springboot.user_management.controller.impl;

import com.springboot.user_management.controller.UserController;
import com.springboot.user_management.entity.User;
import com.springboot.user_management.service.UserService;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserControllerImpl implements UserController {
    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<BaseResponse<List<User>>> getAllUser() {
        return userService.findAllUser();
    }
}
