package com.springboot.user_management.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("pending"),
    PROCESSING("processing"),
    SHIPPED("shipped"),
    COMPLETED("completed"),
    CANCELLED("cancelled"),
    PARTIALLY_COMPLETED("partially completed");

    private final String type;

    OrderStatus(String type) {
        this.type = type;
    }
}
