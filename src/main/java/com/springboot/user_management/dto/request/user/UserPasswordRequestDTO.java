package com.springboot.user_management.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordRequestDTO {

    @NotBlank(message = "Password cũ không được để trống!")
    private String ollPassword;

    @NotBlank(message = "Password mới không được để trống!")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{5,}$",
            message = "Password không hợp lệ!"
    )
    private String newPassword;

    public void trimFields() {
        if (this.ollPassword != null) {
            this.ollPassword = this.ollPassword.trim();
        }
        if (this.newPassword != null) {
            this.newPassword = this.newPassword.trim();
        }
    }
}
