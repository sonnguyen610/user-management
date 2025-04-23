package com.springboot.user_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "shipping_history")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShippingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "shipper_id")
    private Integer shipperId;

    @Column(name = "status")
    private String status;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "note")
    private String note;
}
