package com.springboot.user_management.repository;

import com.springboot.user_management.entity.CustomerOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Integer> {

    @Query(value = "select o.* from customer_order o " +
            "join user u on o.user_id = u.id " +
            "where o.created_at between :startDate and :endDate " +
            "and (:minAmount is null or :maxAmount is null " +
            "or o.total_price between :minAmount and :maxAmount) " +
            "and (:orderStatus is null or :orderStatus = '' or o.status = :orderStatus) " +
            "and (:paymentMethod is null or :paymentMethod = '' or o.payment_method = :paymentMethod) " +
            "and (:customerName is null or :customerName = '' or u.full_name = :customerName)", nativeQuery = true)
    Page<CustomerOrder> findAllByConditions(String orderStatus, String startDate, String endDate, Integer minAmount, Integer maxAmount, String customerName, String paymentMethod, Pageable pageable);
}
