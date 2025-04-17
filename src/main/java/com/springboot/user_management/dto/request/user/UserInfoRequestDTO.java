package com.springboot.user_management.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoRequestDTO {

    @NotBlank(message = "Username không được để trống!")
    @Size(min = 3, max = 10, message = "Username phải có độ dài từ 3 đến 10!")
    private String username;

    @NotBlank(message = "Full name không được để trống!")
    private String fullName;

    @NotBlank(message = "Email không được để trống!")
    @Email(message = "Email không hợp lệ!")
    private String email;

    @NotBlank(message = "Phone number không được để trống!")
    @Pattern(
            regexp = "^0\\d{9}$",
            message = "Phone number không hợp lệ!"
    )
    private String phoneNumber;

    @NotBlank(message = "Address không được để trống!")
    private String address;

    public void trimFields() {
        if (this.username != null) {
            this.username = this.username.trim();
        }
        if (this.fullName != null) {
            this.fullName = this.fullName.trim();
        }
        if (this.email != null) {
            this.email = this.email.trim();
        }
        if (this.phoneNumber != null) {
            this.phoneNumber = this.phoneNumber.trim();
        }
        if (this.address != null) {
            this.address = this.address.trim();
        }
    }
}
