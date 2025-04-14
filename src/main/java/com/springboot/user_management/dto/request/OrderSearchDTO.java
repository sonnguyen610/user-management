package com.springboot.user_management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderSearchDTO {

    private String orderStatus;

    private String startDate;

    private String endDate;

    private Integer minAmount;

    private Integer maxAmount;

    private String customerName;

    private String paymentMethod;

    public void trimFields() {
        if (this.orderStatus != null) {
            this.orderStatus = this.orderStatus.trim();
        }
        if (this.paymentMethod != null) {
            this.paymentMethod = this.paymentMethod.trim();
        }
        if (this.customerName != null) {
            this.customerName = this.customerName.trim();
        }
        if (this.startDate != null) {
            this.startDate = this.startDate.trim();
        }
        if (this.endDate != null) {
            this.endDate = this.endDate.trim();
        }
    }
}
