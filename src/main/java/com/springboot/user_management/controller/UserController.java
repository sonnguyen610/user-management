package com.springboot.user_management.controller;

import com.springboot.user_management.entity.User;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//@RestController
@RequestMapping("/users")
public interface UserController {

    @GetMapping
    ResponseEntity<BaseResponse<List<User>>> getAllUser();
}
