package com.springboot.user_management.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDTO {

    private Integer id;

    private String username;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String address;

    private String token;

    private Boolean status;

    private List<String> roles;
}
