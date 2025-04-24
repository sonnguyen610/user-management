package com.springboot.user_management.dto.request;

import com.springboot.user_management.constant.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    @NotBlank(message = ValidationMessage.ORDER_TYPE_NOT_BLANK)
    private String orderType;

    @NotBlank(message = ValidationMessage.ADDRESS_NOT_BLANK)
    private String shippingAddress;

    @NotBlank(message = ValidationMessage.PAYMENT_NOT_SELECTED)
    @Pattern(
            regexp = "CARD|BANK|CASH_ON_DELIVERY",
            message = ValidationMessage.INVALID_PAYMENT_METHOD
    )
    private String paymentMethod;

    private ProductDTO product;

    private List<Integer> productIds;

    private String voucher;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDTO {
        Integer id;
        Integer quantity;
    }

    public void trimFields() {
        if (this.shippingAddress != null) {
            this.shippingAddress = this.shippingAddress.trim();
        }
        if (this.paymentMethod != null) {
            this.paymentMethod = this.paymentMethod.trim();
        }
        if (this.voucher != null) {
            this.voucher = this.voucher.trim();
        }
    }
}
