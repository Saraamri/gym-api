package com.example.demo.repository;

import com.example.demo.entities.CoursCollectif;
import com.example.demo.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoursRepo extends JpaRepository<CoursCollectif, Long> {
    //List<CoursCollectif> findByUser(UserEntity coach);
}
