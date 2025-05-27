package com.example.demo.serviceImplement;

import com.example.demo.entities.RoleName;
import com.example.demo.entities.UserEntity;
import com.example.demo.repository.UserRepo;
import com.example.demo.services.UserInterface;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.util.UUID;

@Getter
@Setter
@Service
public class UserImplement implements UserInterface {
    private final Path storageLocation = Paths.get("uploads/profile-pictures");

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder encoder;


    public UserImplement() {
        try {
            Files.createDirectories(storageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Impossible de créer le dossier pour stocker les images.", ex);
        }
    }


    @Override
    public UserEntity addUser(UserEntity user, MultipartFile profilePictureFile) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé !");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Nom d'utilisateur déjà utilisé !");
        }
        try {
            // Sauvegarder l'image
            if (profilePictureFile != null && !profilePictureFile.isEmpty()) {

                String fileName = StringUtils.cleanPath(Objects.requireNonNull(profilePictureFile.getOriginalFilename()));
                Path targetLocation = storageLocation.resolve(fileName);
                Files.copy(profilePictureFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                user.setProfilePicture("/uploads/profile-pictures/" + fileName);


            }

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'upload de l'image : " + e.getMessage());
        }

        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @Override
    public UserEntity updateUserWithPicture(Long id, UserEntity user, MultipartFile profilePictureFile) {
        UserEntity existingUser = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Utilisateur non trouvé."));
        existingUser.setEmail(user.getEmail());
        existingUser.setUsername(user.getUsername());

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setTelephone(user.getTelephone());
        existingUser.setSpecialite(user.getSpecialite());

        if (profilePictureFile != null && !profilePictureFile.isEmpty()) {
            try {
                String originalFileName = StringUtils.cleanPath(profilePictureFile.getOriginalFilename());
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
                Path targetLocation = storageLocation.resolve(uniqueFileName);
                Files.copy(profilePictureFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                existingUser.setProfilePicture("/uploads/profile-pictures/" + uniqueFileName);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors du téléchargement de la nouvelle image de profil : " + e.getMessage());
            }
        }

        return userRepository.save(existingUser);
    }




    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserEntity> addListUsers(List<UserEntity> listusers) {
        for (UserEntity user : listusers) {
            if (!userRepository.existsByEmail(user.getEmail())) {
                user.setPassword(encoder.encode(user.getPassword()));
            }
        }
        return userRepository.saveAll(listusers);
    }

    @Override
    public String addUserWTCP(UserEntity user) {
        if (!user.getPassword().equals(user.getConfPassword())) {
            return "Erreur : le mot de passe et la confirmation ne correspondent pas.";
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return "Erreur : email déjà utilisé.";
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return "Utilisateur ajouté avec succès.";
    }

    @Override
    public String addUserWTUN(UserEntity user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "Erreur : nom d'utilisateur déjà utilisé.";
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return "Utilisateur ajouté avec succès.";
    }



    @Override
    public List<UserEntity> getAllusers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserEntity getuserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserEntity> getUsersSW(String username) {
        return userRepository.getUserSW(username);
    }

    @Override
    public List<UserEntity> getUsersByEmail(String email) {
        return userRepository.getUsersByEmail(email);
    }

    @Override
    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    @Override
    public List<UserEntity> getCoachs(RoleName role) {

        return userRepository.findByRole(role);
    }

    @Override
    public List<UserEntity> getAdherents(RoleName role) {
        return userRepository.findByRole(role);
    }

    @Override
    public void changePassword(Long id, String oldPassword, String newPassword) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));

        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Ancien mot de passe incorrect.");
        }

        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }


}
