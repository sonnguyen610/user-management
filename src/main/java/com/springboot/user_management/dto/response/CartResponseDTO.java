package com.springboot.user_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO {

    private Integer productId;

    private String productName;

    private Integer quantity;

    private Boolean status;

    private Boolean outOfStock;
}
