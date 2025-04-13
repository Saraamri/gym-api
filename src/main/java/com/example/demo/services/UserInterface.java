package com.example.demo.services;
import com.example.demo.entities.UserEntity;

import java.util.List;
public interface UserInterface {
    UserEntity addUser(UserEntity user);
    void deleteUser(Long id);
    List<UserEntity> addListUsers(List<UserEntity> listusers);
    public String addUserWTCP(UserEntity user);
    public String addUserWTUN(UserEntity user);
    public UserEntity updateUser(Long id,UserEntity user);
    public    List<UserEntity> getAllusers();
    UserEntity getUser(Long id);
    UserEntity getuserByUsername(String un);
    List<UserEntity> getUsersSW(String un);
    List<UserEntity> getUsersByEmail(String un);

    UserEntity getUserById(Long userId);
}
