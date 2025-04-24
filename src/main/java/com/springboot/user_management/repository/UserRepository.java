package com.springboot.user_management.repository;

import com.springboot.user_management.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from user where status = true", nativeQuery = true)
    List<User> findAllByStatusIsTrue();

    @Query(value = "select username from user " +
            "where :name is null or :name = '' or username like %:name%", nativeQuery = true)
    List<String > findAllUsernameByName(String name);

    boolean existsByUsername(String username);

    boolean existsByEmail(String trim);

    Optional<User> findByUsername(String username);

    User findUserByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, Integer id);

    boolean existsByEmailAndIdNot(String email, Integer id);

    @Query(value = "select distinct u.* from user u " +
            "join user_roles ur on ur.user_id = u.id " +
            "join role r on r.id = ur.role_id " +
            "where u.created_at between :startDate and :endDate " +
            "and (:username is null or :username = '' or u.username = :username) " +
            "and (:fullName is null or :fullName = '' or u.full_name = :fullName) " +
            "and (:role is null or r.name = concat('ROLE_', :role)) " +
            "and (:status is null or u.status = :status) " +
            "order by u.created_at desc", nativeQuery = true)
    Page<User> findAllByConditions(String startDate, String endDate, String username, String fullName, Boolean status, String role, Pageable pageable);

    long countByRoles_NameAndStatusIsTrue(String name);
}
