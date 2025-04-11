
package com.example.demo.repository;

import com.example.demo.entities.Progress;
import com.example.demo.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgressRepo extends JpaRepository<Progress, Long> {
    List<Progress> findByUser(UserEntity user);
}
//........