package com.example.demo.serviceImplement;

import com.example.demo.entities.RendezVous;
import com.example.demo.repository.RendezVousRepository;
import com.example.demo.services.RendezVousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RendezVousServiceImpl implements RendezVousService {
    @Autowired
    private RendezVousRepository repo;

    @Override
    public RendezVous addRendezVous(RendezVous rdv) {
        return repo.save(rdv);
    }

    @Override
    public List<RendezVous> getAllForUser(Long userId) {
        return repo.findByUser_Id(userId);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
