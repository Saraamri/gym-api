package com.example.demo.services;

import com.example.demo.entities.Progress;
import com.example.demo.entities.UserEntity;

import java.util.List;

public interface ProgressInterface {
    Progress saveProgress(Progress progress);
    List<Progress> getProgressByUser(UserEntity user);
    List<Progress> getProgressByObjectif(Long objectifId);
    void deleteProgress(Long id);

    List<Progress> getAllProgress();
}


