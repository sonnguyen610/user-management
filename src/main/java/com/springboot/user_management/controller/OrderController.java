package com.springboot.user_management.controller;

import com.springboot.user_management.dto.request.OrderRequestDTO;
import com.springboot.user_management.dto.request.OrderSearchDTO;
import com.springboot.user_management.dto.response.OrderResponseDTO;
import com.springboot.user_management.dto.response.paging.OrderResponsePagingDTO;
import com.springboot.user_management.utils.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/orders")
public interface OrderController {

    @GetMapping("/{id}")
    ResponseEntity<BaseResponse<OrderResponseDTO>> viewOrderById(@PathVariable Integer id);

    @PostMapping
    ResponseEntity<BaseResponse<OrderResponseDTO>> createOrder(@Valid @RequestBody OrderRequestDTO dto);

    @GetMapping("/search")
    ResponseEntity<BaseResponse<OrderResponsePagingDTO>> getAllOrderByConditions(@RequestBody OrderSearchDTO dto,
                                                                                 @RequestParam(required = false, defaultValue = "1") Integer page,
                                                                                 @RequestParam(required = false, defaultValue = "10") Integer size);
}
