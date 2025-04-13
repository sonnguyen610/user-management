package com.springboot.user_management.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    CARD("card"),
    BANK("bank"),
    CASH_ON_DELIVERY("cash on delivery");

    private final String type;

    PaymentMethod(String type) {
        this.type = type;
    }
}
