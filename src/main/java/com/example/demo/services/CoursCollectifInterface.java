package com.example.demo.services;
import java.util.List;
import com.example.demo.entities.CoursCollectif;
public interface CoursCollectifInterface {
    CoursCollectif addCours(CoursCollectif cours);

    List<CoursCollectif> addListCours(List<CoursCollectif> coursList);

    void deleteCours(Long id);

    CoursCollectif updateCours(Long id, CoursCollectif cours);

    List<CoursCollectif> getAllCours();

    CoursCollectif getCoursById(Long id);

   // List<CoursCollectif> getCoursByCoach(Long coachId);
}
