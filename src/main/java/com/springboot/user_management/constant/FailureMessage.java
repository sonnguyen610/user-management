package com.springboot.user_management.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FailureMessage {
    public static final String FAILURE = "Thất bại!";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error.";
    public static final String PRODUCT_NOT_FOUND = "Product not found!";
    public static final String PRODUCT_LIST_NOT_SELECTED = "Product list not selected!";
    public static final String DATA_NOT_FOUND = "Data not found!";
    public static final String PRODUCT_INACTIVATED = "Cannot update, product has been inactivated!";
    public static final String INVALID_VALUE = "Invalid value!";
    public static final String USER_NOT_FOUND = "User info not found!";
    public static final String ACCESS_DENIED = "Access denied!";
    public static final String MISSING_AUTHORIZATION = "Missing authorization header!";
    public static final String TOKEN_INVALID = "Token expired or invalid!";
    public static final String UNAUTHENTICATED_USER = "Unauthenticated user!";
    public static final String DISABLED_USER = "Your account is already disabled!";
    public static final String DISABLED_PRODUCT = "This product is already disabled!";
    public static final String ROLE_NOT_ALLOWED = "Role type not allowed!";
    public static final String PRODUCT_NOT_IN_CART = "Some products are not in cart";
}
