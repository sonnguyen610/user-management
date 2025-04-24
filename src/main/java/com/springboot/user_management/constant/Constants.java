package com.springboot.user_management.constant;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String VOUCHER_CHARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    public static final int VOUCHER_MAX_LENGTH = 5;

    public static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/api/auth/login",
            "/api/auth/register"
    );

    public static final class ORDER_TYPE {
        public static final String CART = "CART";
        public static final String DIRECT = "DIRECT";
    }
}
