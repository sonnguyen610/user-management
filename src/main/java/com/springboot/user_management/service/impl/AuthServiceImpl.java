package com.springboot.user_management.service.impl;

import com.springboot.user_management.constant.FailureMessage;
import com.springboot.user_management.constant.SuccessMessage;
import com.springboot.user_management.constant.ValidationMessage;
import com.springboot.user_management.dto.request.user.UserLoginRequestDTO;
import com.springboot.user_management.dto.request.user.UserRegisterRequestDTO;
import com.springboot.user_management.dto.response.user.UserLoginResponseDTO;
import com.springboot.user_management.entity.Role;
import com.springboot.user_management.entity.User;
import com.springboot.user_management.mapper.response.UserLoginResponseDtoMapper;
import com.springboot.user_management.repository.RoleRepository;
import com.springboot.user_management.repository.UserRepository;
import com.springboot.user_management.security.JwtUtils;
import com.springboot.user_management.service.AuthService;
import com.springboot.user_management.utils.BaseResponse;
import com.springboot.user_management.utils.ResponseFactory;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class AuthServiceImpl implements AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserLoginResponseDtoMapper userLoginResponseDtoMapper;

    @Override
    public ResponseEntity<BaseResponse<UserLoginResponseDTO>> register(UserRegisterRequestDTO dto) {
        try {
            dto.trimFields();

            User newUser = new User();
            newUser.setUsername(dto.getUsername());
            newUser.setFullName(dto.getFullName());
            newUser.setEmail(dto.getEmail());
            newUser.setPhoneNumber(dto.getPhoneNumber());
            newUser.setAddress(dto.getAddress());
            newUser.setStatus(true);
            newUser.setPassword(passwordEncoder.encode(dto.getPassword()));

            Role role = roleRepository.getReferenceById(1);
            newUser.setRoles(new HashSet<>(Collections.singleton(role)));

            userRepository.save(newUser);
            UserLoginResponseDTO userDto = userLoginResponseDtoMapper.toDTO(newUser);
            userDto.setRoles(newUser.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
            return ResponseFactory.success(HttpStatus.OK, userDto, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<UserLoginResponseDTO>> login(UserLoginRequestDTO dto) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
            UserDetails user = (UserDetails) auth.getPrincipal();

            User userInfo = userRepository.findByUsername(user.getUsername())
                    .orElseThrow(() -> new BadRequestException(FailureMessage.USER_NOT_FOUND));
            UserLoginResponseDTO userDto = userLoginResponseDtoMapper.toDTO(userInfo);
            String token = jwtUtils.generateToken(user);
            userDto.setToken(token);
            userDto.setRoles(userInfo.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
            return ResponseFactory.success(HttpStatus.OK, userDto, SuccessMessage.SUCCESS);
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
