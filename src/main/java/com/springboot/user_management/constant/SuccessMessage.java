package com.springboot.user_management.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SuccessMessage {
    public static final String SUCCESS = "Thành công!";
    public static final String DELETE_SUCCESS = "Successfully deleted.";
    public static final String STATUS_CHANGED = "Status changed.";
    public static final String DATA_UPDATED = "Data updated.";
}
