package com.springboot.user_management.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "vouher")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Voucher extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "percent")
    @NotNull(message = "Phần trăm giảm giá không được để trống.")
    @Min(value = 1, message = "Giảm giá phải lớn hơn 0%.")
    @Max(value = 100, message = "Giảm giá không được vượt quá 100%.")
    private Integer percent;

    @Column(name = "min_amount")
    @NotNull(message = "Giá trị không được để trống.")
    @Min(value = 1, message = "Giá trị tối thiểu phải lớn hơn 0.")
    private Integer minAmount;

    @Column(name = "max_amount")
    @NotNull(message = "Giá trị không được để trống.")
    @Min(value = 1, message = "Giá trị tối thiểu phải lớn hơn 0.")
    private Integer maxAmount;

    @Column(name = "status")
    private Boolean status = true;

    @Column(name = "expiry_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;

    @JsonIgnore
    @AssertTrue(message = "Giá trị tối đa phải lớn hơn tối thiểu.")
    public boolean isMaxGreaterThanMin() {
        if (minAmount == null || maxAmount == null) return true;
        return maxAmount > minAmount;
    }
}
