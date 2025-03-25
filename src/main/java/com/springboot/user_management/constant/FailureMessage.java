package com.springboot.user_management.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FailureMessage {
    public static final String FAILURE = "Thất bại!";
    public static final String NOT_BLANK_FIELD = "This field cannot be blank!";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error.";
    public static final String PRODUCT_PRICE_ERROR = "Price must be greater than 0!";
    public static final String CATEGORY_NOT_SELECTED = "Please select a category!";
    public static final String BRAND_NOT_SELECTED = "Please select a brand!";
    public static final String NAME_EXISTS = "This name already exists!";
    public static final String CODE_EXISTS = "This code already exists!";
}
