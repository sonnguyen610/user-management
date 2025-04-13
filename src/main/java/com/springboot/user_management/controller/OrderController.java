package com.springboot.user_management.controller;

import com.springboot.user_management.dto.request.OrderRequestDTO;
import com.springboot.user_management.dto.response.OrderResponseDTO;
import com.springboot.user_management.utils.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/order")
public interface OrderController {

    @GetMapping("/{id}")
    ResponseEntity<BaseResponse<OrderResponseDTO>> viewOrderById(@PathVariable Integer id);

    @PostMapping
    ResponseEntity<BaseResponse<OrderResponseDTO>> createOrder(@Valid @RequestBody OrderRequestDTO dto);
}
