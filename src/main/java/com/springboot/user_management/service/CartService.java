package com.springboot.user_management.service;

import com.springboot.user_management.dto.request.CartRequestDTO;
import com.springboot.user_management.dto.response.CartResponseDTO;
import com.springboot.user_management.entity.Cart;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {
    ResponseEntity<BaseResponse<Cart>> addToCart(CartRequestDTO dto);

    ResponseEntity<BaseResponse<List<CartResponseDTO>>> getAllCartByUser();
}
