package com.springboot.user_management.service.impl;

import com.springboot.user_management.constant.SuccessMessage;
import com.springboot.user_management.constant.ValidationMessage;
import com.springboot.user_management.dto.request.user.UserRegisterRequestDTO;
import com.springboot.user_management.entity.Role;
import com.springboot.user_management.entity.User;
import com.springboot.user_management.repository.UserRepository;
import com.springboot.user_management.service.AuthService;
import com.springboot.user_management.utils.BaseResponse;
import com.springboot.user_management.utils.ResponseFactory;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<BaseResponse<User>> register(UserRegisterRequestDTO dto) {
        try {
            dto.trimFields();

            User newUser = new User();
            newUser.setUsername(dto.getUsername());
            newUser.setFullName(dto.getFullName());
            newUser.setEmail(dto.getEmail());
            newUser.setPhoneNumber(dto.getPhoneNumber());
            newUser.setAddress(dto.getAddress());
            newUser.setPassword(passwordEncoder.encode(dto.getPassword()));

            Set<Role> roles = new HashSet<>();
            if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {

            } else {
                
            }

            userRepository.save(newUser);
            return ResponseFactory.success(HttpStatus.OK, newUser, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public Map<String, String> validateUserRegister(UserRegisterRequestDTO dto) {
        dto.trimFields();
        Map<String, String> errors = new HashMap<>();

        if (dto.getUsername() != null && userRepository.existsByUsername(dto.getUsername())) {
            errors.put("username", "Username already exists!");
        }
        if (dto.getEmail() != null && userRepository.existsByEmail(dto.getEmail())) {
            errors.put("email", "Email already exists!");
        }

        return errors;
    }
}
