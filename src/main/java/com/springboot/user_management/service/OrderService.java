package com.springboot.user_management.service;

import com.springboot.user_management.dto.request.OrderRequestDTO;
import com.springboot.user_management.dto.response.OrderResponseDTO;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<BaseResponse<OrderResponseDTO>> viewOrderById(Integer id);

    ResponseEntity<BaseResponse<OrderResponseDTO>> createOrder(OrderRequestDTO dto);
}
