package com.springboot.user_management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequestDTO {

    private Integer id;

    private String name;

    private String code;

    private Boolean status;

    private String description;
}
