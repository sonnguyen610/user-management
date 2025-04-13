package com.springboot.user_management.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.user_management.entity.OrderDetail;
import com.springboot.user_management.entity.User;
import com.springboot.user_management.enums.OrderDetailStatus;
import com.springboot.user_management.enums.OrderStatus;
import com.springboot.user_management.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private OrderStatus status;

    private Integer totalPrice;

    private String shippingAddress;

    private PaymentMethod paymentMethod;

    private UserDTO user;

    private List<OrderDetailDTO> orderDetails;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderDetailDTO {
        Integer id;
        Integer quantity;
        Integer productTd;
        String productName;
        OrderDetailStatus status;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDTO {
        Integer id;
        String username;
        String fullName;
    }
}
