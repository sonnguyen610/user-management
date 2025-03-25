package com.springboot.user_management.repository;

import com.springboot.user_management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select * from product where status = true", nativeQuery = true)
    List<Product> findAllByStatusIsTrue();

    boolean existsByName(String name);
}
