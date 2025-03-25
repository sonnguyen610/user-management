package com.springboot.user_management.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FailureMessage {
    public static final String FAILURE = "Thất bại!";
    public static final String NOT_BLANK_FIELD = "This field cannot be blank!";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error.";
}
