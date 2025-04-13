package com.example.demo.services;

import com.example.demo.entities.Progress;
import com.example.demo.entities.UserEntity;

import java.util.List;

public interface ProgressInterface {
    Progress saveProgress(Progress progress);
    List<Progress> getProgressByUser(UserEntity user);

    default void deleteProgress(Long id) {

    }
}
