package com.springboot.user_management.dto.request;

import com.springboot.user_management.constant.ValidationMessage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = ValidationMessage.NAME_NOT_BLANK)
    @Size(min = 5, message = ValidationMessage.NAME_MAX_SIZE)
    private String name;

    private String code;

    private String description;

    @NotNull(message = ValidationMessage.PRICE_NOT_BLANK)
    @Min(value = 500, message = ValidationMessage.PRICE_MAX_VALUE)
    private Integer price;

    private Integer quantity;

    private Boolean status;

    @NotNull(message = ValidationMessage.CATEGORY_NOT_SELECTED)
    private Integer category;

    @NotNull(message = ValidationMessage.BRAND_NOT_SELECTED)
    private Integer brand;

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
    }
}
