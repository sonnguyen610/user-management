package com.springboot.user_management.repository;

import com.springboot.user_management.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);

    @Query(value = "select * from product where id in :ids", nativeQuery = true)
    List<Product> findAllByIds(List<Integer> ids);

    @Query(value = "select * from product " +
            "where (:name is null or :name = '' or name like %:name%) " +
            "and created_at between :startDate and :endDate " +
            "and (:status is null or status = :status) " +
            "and created_by = :username order by created_at desc", nativeQuery = true)
    Page<Product> findAllByNameAndStatusAndDate(String name, Boolean status, String startDate, String endDate, String username, Pageable pageable);

    Optional<Product> findByIdAndStatusIsTrue(Integer id);

    List<Product> findAllByCreatedBy(String createdBy);

    boolean existsByIdAndStatusIsTrue(Integer id);

    Product findProductById(Integer id);
}
