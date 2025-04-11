package com.springboot.user_management.service.impl;

import com.springboot.user_management.constant.FailureMessage;
import com.springboot.user_management.constant.SuccessMessage;
import com.springboot.user_management.entity.User;
import com.springboot.user_management.repository.UserRepository;
import com.springboot.user_management.service.UserService;
import com.springboot.user_management.utils.BaseResponse;
import com.springboot.user_management.utils.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<BaseResponse<List<User>>> findAllUser() {
        try {
            List<User> userList = userRepository.findAllByStatusIsTrue();
            return ResponseFactory.success(HttpStatus.OK, userList, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, FailureMessage.FAILURE);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<List<String>>> getAllUsername(String name) {
        try {
            List<String> usernameList = userRepository.findAllUsernameByName(name);
            return ResponseFactory.success(HttpStatus.OK, usernameList, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, FailureMessage.FAILURE);
        }
    }
}
