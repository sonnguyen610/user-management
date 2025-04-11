package com.springboot.user_management.repository;

import com.springboot.user_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from user where status = true", nativeQuery = true)
    List<User> findAllByStatusIsTrue();

    @Query(value = "select username from user " +
            "where :name is null or :name = '' or username like %:name%", nativeQuery = true)
    List<String > findAllUsernameByName(String name);
}
