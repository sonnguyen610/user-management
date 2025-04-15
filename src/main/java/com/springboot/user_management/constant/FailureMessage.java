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
    public static final String PRODUCT_NOT_FOUND = "Product not found!";
    public static final String CATEGORY_NOT_FOUND = "Category not found!";
    public static final String BRAND_NOT_FOUND = "Brand not found!";
    public static final String PRODUCT_LIST_NOT_SELECTED = "Product list not selected!";
    public static final String DATA_NOT_FOUND = "Data not found!";
    public static final String PRODUCT_INACTIVATED = "Cannot update, product has been inactivated!";
    public static final String NOT_BLANK_VALUE = "Value cannot be blank!";
    public static final String INVALID_VALUE = "Invalid value!";
    public static final String USER_NOT_FOUND = "User info not found!";
    public static final String ACCESS_DENIED = "Access denied!";
    public static final String MISSING_AUTHORIZATION = "Missing authorization header!";
    public static final String TOKEN_INVALID = "Token expired or invalid!";
}
