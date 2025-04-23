package com.springboot.user_management.service.impl;

import com.springboot.user_management.config.SecurityUtils;
import com.springboot.user_management.constant.FailureMessage;
import com.springboot.user_management.constant.SuccessMessage;
import com.springboot.user_management.constant.ValidationMessage;
import com.springboot.user_management.dto.request.CartRequestDTO;
import com.springboot.user_management.dto.response.CartResponseDTO;
import com.springboot.user_management.entity.Cart;
import com.springboot.user_management.entity.Product;
import com.springboot.user_management.entity.User;
import com.springboot.user_management.mapper.response.CartResponseDtoMapper;
import com.springboot.user_management.repository.CartRepository;
import com.springboot.user_management.repository.ProductRepository;
import com.springboot.user_management.repository.UserRepository;
import com.springboot.user_management.service.CartService;
import com.springboot.user_management.utils.BaseResponse;
import com.springboot.user_management.utils.ResponseFactory;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartResponseDtoMapper cartResponseDtoMapper;

    @Override
    public ResponseEntity<BaseResponse<Cart>> addToCart(CartRequestDTO dto) {
        try {
            if (!productRepository.existsByIdAndStatusIsTrue(dto.getProductId())) {
                throw new BadRequestException(FailureMessage.PRODUCT_NOT_FOUND);
            }

            String username = SecurityUtils.getUsername();
            User user = userRepository.findUserByUsername(username);
            Product product = productRepository.findProductById(dto.getProductId());

            if (dto.getQuantity() > product.getQuantity()) {
                throw new BadRequestException(ValidationMessage.OUT_OF_STOCK);
            }

            Cart cart = new Cart();
            cart.setProductId(product.getId());
            cart.setProductName(product.getName());
            cart.setUserId(user.getId());
            cart.setPrice(product.getPrice());
            cart.setQuantity(dto.getQuantity());
            cartRepository.save(cart);
            return ResponseFactory.success(HttpStatus.OK, cart, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<List<CartResponseDTO>>> getAllCartByUser() {
        try {
            String username = SecurityUtils.getUsername();
            User user = userRepository.findUserByUsername(username);

            List<Cart> cartList = cartRepository.findAllByUserId(user.getId());
            List<CartResponseDTO> responseDTO = cartResponseDtoMapper.toListDTO(cartList);
            if (!responseDTO.isEmpty()) {
                for (CartResponseDTO dto : responseDTO) {
                    Product product = productRepository.findById(dto.getProductId()).orElse(null);
                    dto.setStatus(product.getStatus());
                    dto.setOutOfStock(dto.getQuantity() > product.getQuantity());
                    if (!dto.getOutOfStock()) {
                        dto.setQuantity(0);
                    }
                }
            }
            return ResponseFactory.success(HttpStatus.BAD_REQUEST, responseDTO, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }
}
