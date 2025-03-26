package com.springboot.user_management.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductResponseForParentDTO extends ProductResponseDTO {

    @JsonIgnore
    private CategotyDTO category;

    @JsonIgnore
    private BrandDTO brand;
}
