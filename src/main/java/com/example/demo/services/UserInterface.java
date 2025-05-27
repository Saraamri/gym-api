package com.example.demo.services;

import com.example.demo.entities.RoleName;
import com.example.demo.entities.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserInterface {

    UserEntity addUser(UserEntity user,MultipartFile profilePicture);
    //String saveProfilePicture(MultipartFile profilePicture);

    void deleteUser(Long id);

    List<UserEntity> addListUsers(List<UserEntity> listusers);

    String addUserWTCP(UserEntity user); // avec confirmation de mot de passe

    String addUserWTUN(UserEntity user); // vérifie unicité username

    UserEntity updateUserWithPicture(Long id, UserEntity user, MultipartFile profilePicture);



    List<UserEntity> getAllusers();

    UserEntity getUser(Long id);

    UserEntity getuserByUsername(String username);

    List<UserEntity> getUsersSW(String usernameStartWith);

    List<UserEntity> getUsersByEmail(String email);

    UserEntity getUserById(Long userId);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);


    List<UserEntity> getCoachs(RoleName roleName);

    List<UserEntity> getAdherents(RoleName roleName);

    void changePassword(Long id, String oldPassword, String newPassword);
}
