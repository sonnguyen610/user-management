package com.springboot.user_management.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidationException extends RuntimeException {
    private Map<String, String> datas;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Map<String, String> datas, String message) {
        super(message);
        this.datas = datas;
    }
}
