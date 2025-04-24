package com.springboot.user_management.controller;

import com.springboot.user_management.dto.request.user.UserInfoRequestDTO;
import com.springboot.user_management.dto.request.user.UserPasswordRequestDTO;
import com.springboot.user_management.dto.response.paging.UserResponsePagingDTO;
import com.springboot.user_management.dto.response.user.UserResponseDTO;
import com.springboot.user_management.utils.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/users")
public interface UserController {

    @GetMapping
    ResponseEntity<BaseResponse<UserResponseDTO>> getUserInfo();

    @PutMapping("/change-password")
    ResponseEntity<BaseResponse<UserResponseDTO>> changeUserPassword(@Valid @RequestBody UserPasswordRequestDTO dto, BindingResult bindingResult);

    @PutMapping
    ResponseEntity<BaseResponse<UserResponseDTO>> updateUserInfo(@Valid @RequestBody UserInfoRequestDTO dto, BindingResult bindingResult);

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    ResponseEntity<BaseResponse<UserResponsePagingDTO>> getAllUserByConditions(@RequestParam String startDate, @RequestParam String endDate,
                                                                               @RequestParam(required = false) String username,
                                                                               @RequestParam(required = false) String fullName,
                                                                               @RequestParam(required = false) Boolean status,
                                                                               @RequestParam(required = false) String role,
                                                                               @RequestParam(required = false, defaultValue = "1") Integer page,
                                                                               @RequestParam(required = false, defaultValue = "10") Integer size);

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/update-status")
    ResponseEntity<BaseResponse<UserResponseDTO>> updateUserStatus(@PathVariable Integer id, @RequestParam Boolean status);

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/update-role")
    ResponseEntity<BaseResponse<UserResponseDTO>> updateUserRole(@PathVariable Integer id, @RequestParam String role);
}
