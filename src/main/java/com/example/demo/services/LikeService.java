package com.example.demo.services;



import com.example.demo.entities.CoursCollectif;
import com.example.demo.entities.Like;
import com.example.demo.entities.UserEntity;
import com.example.demo.repository.CoursRepo;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final LikeRepository likeRepo;
    private final UserRepo userRepo;
    private final CoursRepo coursRepo;

    public LikeService(LikeRepository likeRepo, UserRepo userRepo, CoursRepo coursRepo) {
        this.likeRepo = likeRepo;
        this.userRepo = userRepo;
        this.coursRepo = coursRepo;
    }

    public void likeCours(Long coursId, Long userId) {
        CoursCollectif cours = coursRepo.findById(coursId).orElseThrow();
        UserEntity user = userRepo.findById(userId).orElseThrow();

        if (!likeRepo.existsByCoursAndUser(cours, user)) {
            Like like = new Like();
            like.setCours(cours);
            like.setUser(user);
            likeRepo.save(like);
        }
    }
    @Transactional
    public void unlikeCours(Long coursId, Long userId) {
        CoursCollectif cours = coursRepo.findById(coursId).orElseThrow();
        UserEntity user = userRepo.findById(userId).orElseThrow();
        likeRepo.deleteByCoursAndUser(cours, user);
    }

    public boolean hasUserLiked(Long coursId, Long userId) {
        CoursCollectif cours = coursRepo.findById(coursId).orElseThrow();
        UserEntity user = userRepo.findById(userId).orElseThrow();
        return likeRepo.existsByCoursAndUser(cours, user);
    }

    public long countLikes(Long coursId) {
        CoursCollectif cours = coursRepo.findById(coursId).orElseThrow();
        return likeRepo.countByCours(cours);
    }
}

