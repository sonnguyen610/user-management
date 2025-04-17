package com.springboot.user_management.service;

import com.springboot.user_management.dto.request.user.UserInfoRequestDTO;
import com.springboot.user_management.dto.request.user.UserPasswordRequestDTO;
import com.springboot.user_management.dto.response.paging.UserResponsePagingDTO;
import com.springboot.user_management.dto.response.user.UserResponseDTO;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService {
    ResponseEntity<BaseResponse<UserResponseDTO>> getUserInfo();

    ResponseEntity<BaseResponse<UserResponseDTO>> changeUserPassword(UserPasswordRequestDTO dto);

    ResponseEntity<BaseResponse<UserResponseDTO>> updateUserInfo(UserInfoRequestDTO dto);

    ResponseEntity<BaseResponse<UserResponsePagingDTO>> getAllUserByConditions(String startDate, String endDate, String username, String fullName, Boolean status, String role, Integer page, Integer size);

    ResponseEntity<BaseResponse<UserResponseDTO>> updateUserStatus(Integer id, Boolean status);

    ResponseEntity<BaseResponse<UserResponseDTO>> updateUserRole(Integer id, String role);
}
