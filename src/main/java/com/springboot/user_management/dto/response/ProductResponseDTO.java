package com.springboot.user_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

    private Integer id;

    private String  name;

    private String code;

    private String description;

    private BigDecimal price;

    private Integer quantity;

    private Boolean status;

    private String categoryName;

    private String brandName;
}
