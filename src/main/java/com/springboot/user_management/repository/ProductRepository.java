package com.springboot.user_management.repository;

import com.springboot.user_management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);

    @Query(value = "select * from product where id in :ids", nativeQuery = true)
    List<Product> findAllByIds(List<Integer> ids);
}
