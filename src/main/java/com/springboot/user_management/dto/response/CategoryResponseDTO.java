package com.springboot.user_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO {

    private Integer id;

    private String name;

    private String code;

    private String description;

    private String createdBy;

    private Boolean status;

    private List<ProductResponseForParentDTO> products;
}
