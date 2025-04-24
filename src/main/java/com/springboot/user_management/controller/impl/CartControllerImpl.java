package com.springboot.user_management.controller.impl;

import com.springboot.user_management.constant.ValidationMessage;
import com.springboot.user_management.controller.CartController;
import com.springboot.user_management.dto.request.CartRequestDTO;
import com.springboot.user_management.dto.response.CartResponseDTO;
import com.springboot.user_management.entity.Cart;
import com.springboot.user_management.exception.ValidationException;
import com.springboot.user_management.service.CartService;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CartControllerImpl implements CartController {
    @Autowired
    private CartService cartService;

    @Override
    public ResponseEntity<BaseResponse<Cart>> addToCart(CartRequestDTO dto, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors, ValidationMessage.VALIDATION_FAILED);
        }

        return cartService.addToCart(dto);
    }

    @Override
    public ResponseEntity<BaseResponse<List<CartResponseDTO>>> getCartList() {
        return cartService.getAllCartByUser();
    }
}
