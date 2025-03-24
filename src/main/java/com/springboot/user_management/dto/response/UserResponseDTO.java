package com.springboot.user_management.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class UserResponseDTO {

    private Integer id;

    private String username;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String address;

    private String birthYear;

    private Boolean status;
}
