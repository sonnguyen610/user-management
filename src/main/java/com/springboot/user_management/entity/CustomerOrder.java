package com.springboot.user_management.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.user_management.enums.OrderStatus;
import com.springboot.user_management.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "customer_order")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "total_price")
    private Integer totalPrice;

    @Column(name = "address")
    private String shippingAddress;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customerOrder", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
}
