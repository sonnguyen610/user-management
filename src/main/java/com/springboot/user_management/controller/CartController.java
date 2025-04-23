package com.springboot.user_management.controller;

import com.springboot.user_management.dto.request.CartRequestDTO;
import com.springboot.user_management.dto.response.CartResponseDTO;
import com.springboot.user_management.entity.Cart;
import com.springboot.user_management.utils.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/carts")
public interface CartController {

    @PostMapping
    ResponseEntity<BaseResponse<Cart>> addToCart(@Valid @RequestBody CartRequestDTO dto, BindingResult bindingResult);

    @GetMapping
    ResponseEntity<BaseResponse<List<CartResponseDTO>>> getCartList();
}
