package com.example.demo.serviceImplement;
import com.example.demo.entities.CoursCollectif;
import com.example.demo.entities.UserEntity;
import com.example.demo.repository.CoursRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.services.CoursCollectifInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
 @Service
public class CoursImplement implements CoursCollectifInterface {

    @Autowired
    private CoursRepo coursRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public CoursCollectif addCours(CoursCollectif cours) {
        return coursRepo.save(cours);
    }

    @Override
    public List<CoursCollectif> addListCours(List<CoursCollectif> coursList) {
        return coursRepo.saveAll(coursList);
    }

    @Override
    public void deleteCours(Long id) {
        coursRepo.deleteById(id);
    }

    @Override
    public CoursCollectif updateCours(Long id, CoursCollectif cours) {
        Optional<CoursCollectif> existing = coursRepo.findById(id);
        if (existing.isPresent()) {
            cours.setId(id);
            return coursRepo.save(cours);
        } else {
            throw new RuntimeException("Cours non trouvé avec l'ID : " + id);
        }
    }

    @Override
    public List<CoursCollectif> getAllCours() {
        return coursRepo.findAll();
    }

    @Override
    public CoursCollectif getCoursById(Long id) {
        return coursRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé avec l'ID : " + id));
    }


}

