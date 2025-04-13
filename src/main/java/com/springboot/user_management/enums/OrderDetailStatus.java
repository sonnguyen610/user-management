package com.springboot.user_management.enums;

import lombok.Getter;

@Getter
public enum OrderDetailStatus {
    AVAILABLE("available"),
    SHIPPED("shipped"),
    RETURNED("returned"),
    CANCELLED("cancelled");

    private final String type;

    OrderDetailStatus(String type) {
        this.type = type;
    }
}
