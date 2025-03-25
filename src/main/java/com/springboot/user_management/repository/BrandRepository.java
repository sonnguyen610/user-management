package com.springboot.user_management.repository;

import com.springboot.user_management.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    boolean existsByIdAndStatusIsTrue(Integer id);

    boolean existsByName(String name);

    boolean existsByCode(String code);
}
