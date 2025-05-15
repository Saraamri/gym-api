package com.example.demo.controllers;

import com.example.demo.entities.RoleName;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.UserInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserInterface userInterface;

    @PostMapping(path = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> addUser(
            @RequestPart("user") @Valid UserEntity user,
            @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture
    ){
        if (userInterface.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Erreur : L'email est déjà utilisé !"));
        }
        if (userInterface.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Erreur : Le nom d'utilisateur est déjà utilisé !"));
        }

        UserEntity savedUser = userInterface.addUser(user, profilePicture);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userInterface.deleteUser(id);
    }

    @PostMapping("/addList")
    public List<UserEntity> addListUsers(@RequestBody List<UserEntity> users) {
        return userInterface.addListUsers(users);
    }


    @PutMapping(path = "/updateWithPicture/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateUserWithPicture(
            @PathVariable Long id,
            @RequestPart("user") @Valid UserEntity user,
            @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture
    ) {
        UserEntity updatedUser = userInterface.updateUserWithPicture(id, user, profilePicture);
        return ResponseEntity.ok(updatedUser);
    }




    @GetMapping("/all")
    public List<UserEntity> getAllUsers() {
        return userInterface.getAllusers();
    }

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userInterface.getUser(id);
    }

    @GetMapping("/username/{username}")
    public UserEntity getUserByUsername(@PathVariable String username) {
        return userInterface.getuserByUsername(username);
    }

    @GetMapping("/search/username/{username}")
    public List<UserEntity> getUsersContainingUsername(@PathVariable String username) {
        return userInterface.getUsersSW(username);
    }

    @GetMapping("/search/email")
    public List<UserEntity> getUsersByEmail(@RequestParam String email) {
        return userInterface.getUsersByEmail(email);
    }

    @GetMapping("/coachs")
    public List<UserEntity> getAllCoachs(@RequestParam("role") String role) {
        RoleName roleName = RoleName.valueOf(role);  // Convertir la chaîne en RoleName
        return userInterface.getCoachs(roleName);
    }
    @GetMapping("/adherents")
    public List<UserEntity> getAllAdherents(@RequestParam("role") String role) {
        RoleName roleName = RoleName.valueOf(role);  // Convertir la chaîne en RoleName
        return userInterface.getAdherents(roleName);
    }

}
