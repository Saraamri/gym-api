package com.example.demo.repository;
import com.example.demo.entities.CoursCollectif;
import com.example.demo.entities.Like;
import com.example.demo.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    long countByCours(CoursCollectif cours);
    Optional<Like> findByCoursAndUser(CoursCollectif cours, UserEntity user);
    boolean existsByCoursAndUser(CoursCollectif cours, UserEntity user);
    void deleteByCoursAndUser(CoursCollectif cours, UserEntity user);
}

