package com.example.demo.repository;

import com.example.demo.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
    UserEntity findByUsername(String username);


    @Query(value = "SELECT * FROM users u WHERE u.username LIKE :cle%", nativeQuery = true)
    List<UserEntity> getUserSW(@Param("cle") String un);


    @Query(value = "SELECT * FROM users u WHERE u.email LIKE %:domain%", nativeQuery = true)
    List<UserEntity> getUsersByEmail(@Param("domain") String un);


}
