package com.springboot.user_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Integer id;

    private String username;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String address;

    private String password;

    private Boolean status;
}
