package com.springboot.user_management.constant;

public class ValidationMessage {
    public static final String VALIDATION_FAILED = "Validation failed!";
    public static final String NAME_NOT_BLANK = "Name cannot be blank!";
    public static final String CODE_NOT_BLANK = "Code cannot be blank!";
    public static final String NAME_MAX_SIZE = "Name must be at least 5 characters!";
    public static final String CODE_MAX_SIZE = "Code must be between 2 and 5 characters!";
    public static final String NAME_EXISTS = "This name already exists!";
    public static final String CODE_EXISTS = "This code already exists!";
    public static final String PRICE_NOT_BLANK = "Price cannot be blank!";
    public static final String PRICE_MAX_VALUE = "Price must be at least 500!";
    public static final String CATEGORY_NOT_SELECTED = "Please select a category!";
    public static final String BRAND_NOT_SELECTED = "Please select a brand!";
    public static final String CATEGORY_NOT_EXISTS_OR_INACTIVATED = "Category not exists or inactivated!";
    public static final String BRAND_NOT_EXISTS_OR_INACTIVATED = "Brand not exists or inactivated!";
    public static final String ADDRESS_NOT_BLANK = "Shipping address cannot be blank!";
    public static final String PAYMENT_NOT_SELECTED = "Payment method not selected!";
    public static final String PRODUCT_LIST_NOT_NULL = "Product list must not be null!";
    public static final String PRODUCT_NOT_SELECTED = "Please select at least 1 product!";
    public static final String INVALID_PAYMENT_METHOD = "Invalid payment method";
    public static final String NOT_SELECT_PRODUCT = "Please select at least 1 product!";
    public static final String OUT_OF_STOCK = "Product already out of stock!";
}
