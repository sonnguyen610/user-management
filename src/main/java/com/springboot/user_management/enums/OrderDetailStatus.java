package com.springboot.user_management.enums;

import lombok.Getter;

@Getter
public enum OrderDetailStatus {
    // đợi xác thực
    PENDING,

    // đang giao
    SHIPPED,

    // hoàn tất
    COMPLETED,

    //trả hàng
    RETURNED,

    // huỷ bỏ
    CANCELLED;
}
