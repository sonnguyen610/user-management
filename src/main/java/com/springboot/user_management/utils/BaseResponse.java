package com.springboot.user_management.utils;

import lombok.*;

@Data
@Builder
public class BaseResponse<T> {
    public boolean success;
    private int code;
    private T data;
    private String message;
}
