package com.example.demo.serviceImplement;

import com.example.demo.entities.Progress;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.ObjectifInterface;
import com.example.demo.services.ProgressInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.ProgressRepo;
import java.util.List;

@Service
public class ProgressImplement implements ProgressInterface {

    @Autowired
    private ProgressRepo progressRepository;



    @Override
    public Progress saveProgress(Progress progress) {
        return progressRepository.save(progress);
    }

    @Override
    public List<Progress> getProgressByUser(UserEntity user) {
        return progressRepository.findByUser(user);
    }

    @Override
    public void deleteProgress(Long id) {
        progressRepository.deleteById(id);
    }
    @Override
    public List<Progress> getProgressByObjectif(Long objectifId) {
        return progressRepository.findByObjectifId(objectifId); // ✅ Corrigé
    }

    @Override
    public List<Progress> getAllProgress() {
        return progressRepository.findAll();
    }




}

