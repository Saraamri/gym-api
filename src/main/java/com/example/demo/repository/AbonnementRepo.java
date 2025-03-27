package com.example.demo.repository;
import com.example.demo.entities.Abonnement;
import com.example.demo.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AbonnementRepo extends JpaRepository<Abonnement, Long> {
    List<Abonnement> findByUser(UserEntity user);


}
