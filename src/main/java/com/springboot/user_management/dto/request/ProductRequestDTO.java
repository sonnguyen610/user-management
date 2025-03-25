package com.springboot.user_management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

    private Integer id;

    private String  name;

    private String code;

    private String description;

    private BigDecimal price;

    private Integer quantity;

    private Boolean status;

    private Integer categoryId;

    private Integer brandId;
}
