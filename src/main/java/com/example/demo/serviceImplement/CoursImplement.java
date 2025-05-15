package com.example.demo.serviceImplement;

import com.example.demo.entities.CoursCollectif;
import com.example.demo.repository.CoursRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.services.CoursCollectifInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class CoursImplement implements CoursCollectifInterface {

    private final Path storageLocation = Paths.get("uploads/coursCollectifs");

    @Autowired
    private CoursRepo coursRepo;

    @Autowired
    private UserRepo userRepo;

    public CoursImplement() {
        try {
            Files.createDirectories(storageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Impossible de créer le dossier pour stocker les images.", ex);
        }
    }

    // Nouvelle méthode pour ajouter un cours avec image
    @Override
    public CoursCollectif addCours(CoursCollectif cours, MultipartFile imageFile) {
        try {
            // Sauvegarder l'image
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));
            Path targetLocation = storageLocation.resolve(fileName);
            Files.copy(imageFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Définir l'URL de l'image dans le cours
            cours.setImage("/uploads/coursCollectifs/" + fileName);

            // Sauvegarder le cours en BDD
            return coursRepo.save(cours);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'upload de l'image : " + e.getMessage());
        }
    }

    // Tu gardes ta méthode ancienne si tu veux ajouter sans image
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
    public CoursCollectif updateCoursWithImage(Long id, CoursCollectif cours, MultipartFile imageFile) {
        CoursCollectif existing = coursRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé"));

        // Mettre à jour les champs
        existing.setNom(cours.getNom());
        existing.setDescription(cours.getDescription());
        existing.setDureeTotale(cours.getDureeTotale());
        existing.setNiveau(cours.getNiveau());
        existing.setJours(cours.getJours());
        existing.setHoraire(cours.getHoraire());

        // Gérer l'image
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path imagePath = Paths.get("uploads/coursCollectifs", fileName);
            try {
                Files.createDirectories(imagePath.getParent());
                Files.write(imagePath, imageFile.getBytes());
                existing.setImage("/uploads/coursCollectifs/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de l'enregistrement de l'image", e);
            }
        }

        return coursRepo.save(existing);
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

    @Override
    public List<CoursCollectif> getCoursByCoach(Long coachId) {
        return coursRepo.findByCoach_Id(coachId);
    }
}
