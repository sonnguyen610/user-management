package com.springboot.user_management.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    // đợi xác thực
    PENDING,

    // đã xác nhận
    CONFIRMED,

    // đang giao
    SHIPPED,

    // đã giao xong
    DELIVERED,

    // hoàn tất
    COMPLETED,

    // huỷ bỏ
    CANCELLED,

    // giao được một phần
    PARTIALLY_COMPLETED;
}
