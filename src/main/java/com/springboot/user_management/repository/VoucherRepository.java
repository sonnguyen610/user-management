package com.springboot.user_management.repository;

import com.springboot.user_management.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    Optional<Voucher> findByIdAndStatusIsTrue(Integer id);

    Optional<Voucher> findByCodeAndStatusIsTrue(String code);
}
