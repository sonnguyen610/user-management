package com.springboot.user_management.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductResponseForParentDTO extends ProductResponseDTO {

    @JsonIgnore
    private CategoryDTO category;

    @JsonIgnore
    private BrandDTO brand;
}
