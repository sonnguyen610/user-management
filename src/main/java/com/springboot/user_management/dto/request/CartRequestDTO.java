package com.springboot.user_management.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartRequestDTO {

    @NotNull(message = "Product cannot be null.")
    private Integer productId;

    @NotNull(message = "Quantity cannot be null.")
    @Min(value = 1, message = "Please select at least 1 product.")
    private Integer quantity;
}
