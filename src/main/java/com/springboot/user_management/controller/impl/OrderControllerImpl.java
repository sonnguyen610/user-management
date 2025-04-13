package com.springboot.user_management.controller.impl;

import com.springboot.user_management.controller.OrderController;
import com.springboot.user_management.dto.request.OrderRequestDTO;
import com.springboot.user_management.dto.response.OrderResponseDTO;
import com.springboot.user_management.service.OrderService;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderControllerImpl implements OrderController {
    @Autowired
    private OrderService orderService;

    @Override
    public ResponseEntity<BaseResponse<OrderResponseDTO>> viewOrderById(Integer id) {
        return orderService.viewOrderById(id);
    }

    @Override
    public ResponseEntity<BaseResponse<OrderResponseDTO>> createOrder(OrderRequestDTO dto) {
        return orderService.createOrder(dto);
    }
}
