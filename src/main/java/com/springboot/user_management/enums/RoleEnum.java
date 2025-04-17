package com.springboot.user_management.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum RoleEnum {
    ADMIN("ROLE_ADMIN"),
    OWNER("ROLE_OWNER"),
    SHIPPER("ROLE_SHIPPER"),
    MEMBER("ROLE_MEMBER");

    private final String type;

    RoleEnum(String type) {
        this.type = type;
    }

    public static List<String> getRoleNames() {
        return Arrays.stream(RoleEnum.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public static String getTypeFromName(String name) {
        return Arrays.stream(RoleEnum.values())
                .filter(role -> role.name().equals(name))
                .map(role -> role.type)
                .findFirst()
                .orElse(null);
    }
}
