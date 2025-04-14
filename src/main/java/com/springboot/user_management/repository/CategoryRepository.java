package com.springboot.user_management.repository;

import com.springboot.user_management.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByIdAndStatusIsTrue(Integer id);

    boolean existsByName(String name);

    boolean existsByCode(String code);

    boolean existsByNameAndIdNot(String name, Integer id);

    @Query(value = "select * from category " +
            "where (:name is null or :name = '' or name like %:name%) " +
            "and (:createdBy is null or :createdBy = '' or created_by = :createdBy) " +
            "and (:status is null or status = :status) " +
            "and created_at >= :date order by created_at desc", nativeQuery = true)
    Page<Category> findAllByConditions(String name, String createdBy, Boolean status, String date, Pageable pageable);
}
