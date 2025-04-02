package com.springboot.user_management.dto.request;

import com.springboot.user_management.constant.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequestDTO {

    @NotBlank(message = ValidationMessage.NAME_NOT_BLANK)
    @Size(min = 5, message = ValidationMessage.NAME_MAX_SIZE)
    private String name;

    @NotBlank(message = ValidationMessage.CODE_NOT_BLANK)
    @Size(min = 2, max = 5, message = ValidationMessage.CODE_MAX_SIZE)
    private String code;

    private Boolean status;

    private String description;

    private String createdBy;

    public void trimFields() {
        if (this.name != null) {
            this.name = this.name.trim();
        }
        if (this.code != null) {
            this.code = this.code.trim();
        }
        if (this.description != null) {
            this.description = this.description.trim();
        }
        if (this.createdBy != null) {
            this.createdBy = this.createdBy.trim();
        }
    }
}
