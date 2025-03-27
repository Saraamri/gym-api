package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.Reservation;
import java.util.List;
public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    List<Reservation> findByAdherentId(Long adherentId);
    List<Reservation> findByCoursId(Long coursId);
}
