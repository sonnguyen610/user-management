package com.springboot.user_management.constant;

public class Constants {
    public static final String VOUCHER_CHARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    public static final int VOUCHER_MAX_LENGTH = 5;

    public static class Role {
        public static final String ADMIN = "ADMIN";
        public static final String OWNER = "OWNER";
        public static final String SHIPPER = "SHIPPER";
        public static final String MEMBER = "MEMBER";
    }
}
