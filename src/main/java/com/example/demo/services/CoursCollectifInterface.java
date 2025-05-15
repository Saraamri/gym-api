package com.example.demo.services;
import java.util.List;
import com.example.demo.entities.CoursCollectif;
import org.springframework.web.multipart.MultipartFile;

public interface CoursCollectifInterface {
    CoursCollectif addCours(CoursCollectif cours);
    CoursCollectif addCours(CoursCollectif cours, MultipartFile imageFile);

    List<CoursCollectif> addListCours(List<CoursCollectif> coursList);

    void deleteCours(Long id);

    CoursCollectif updateCoursWithImage(Long id, CoursCollectif cours, MultipartFile imageFile);


    List<CoursCollectif> getAllCours();

    CoursCollectif getCoursById(Long id);

    List<CoursCollectif> getCoursByCoach(Long coachId);


}
