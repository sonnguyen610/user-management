package com.springboot.user_management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchDTO {

    private String name;

    private List<Integer> brand;

    private List<Integer> category;

    private Integer minPrice;

    private Integer maxPrice;

    private String sortBy;

    private String sortType;
}
