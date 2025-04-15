package com.springboot.user_management.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDTO {

    @NotBlank(message = "Username không được để trống!")
    private String username;

    @NotBlank(message = "Password không được để trống!")
    private String password;

    public void trimFields() {
        if (this.username != null) {
            this.username = this.username.trim();
        }
        if (this.password != null) {
            this.password = this.password.trim();
        }
    }
}
