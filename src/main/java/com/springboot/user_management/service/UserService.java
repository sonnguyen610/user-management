package com.springboot.user_management.service;

import com.springboot.user_management.entity.User;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<BaseResponse<List<User>>> findAllUser();
}
