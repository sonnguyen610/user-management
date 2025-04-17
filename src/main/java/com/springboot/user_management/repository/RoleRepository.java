package com.springboot.user_management.repository;

import com.springboot.user_management.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String member);

    @Query(value = "select * from role where name = :name", nativeQuery = true)
    Role findRoleByName(String member);
}
