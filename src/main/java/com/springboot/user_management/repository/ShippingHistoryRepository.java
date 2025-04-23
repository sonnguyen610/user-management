package com.springboot.user_management.repository;

import com.springboot.user_management.entity.ShippingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShippingHistoryRepository extends JpaRepository<ShippingHistory, Integer> {

    @Query(value = "select shipper_id from shipping_history " +
            "where order_id = :id limit 1", nativeQuery = true)
    Integer findShipperIdByOrderId(Integer id);
}
