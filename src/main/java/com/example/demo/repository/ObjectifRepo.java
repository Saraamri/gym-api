package com.example.demo.repository;

import com.example.demo.entities.Objectif;
import com.example.demo.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ObjectifRepo extends JpaRepository<Objectif, Long> {
    List<Objectif> findByUser(UserEntity user); // Trouver les objectifs d'un utilisateur
}
