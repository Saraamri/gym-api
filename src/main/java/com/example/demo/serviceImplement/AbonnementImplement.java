package com.example.demo.serviceImplement;
import com.example.demo.entities.Abonnement;
import com.example.demo.entities.UserEntity;
import com.example.demo.repository.AbonnementRepo;
import com.example.demo.services.AbonnementInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AbonnementImplement implements AbonnementInterface {


        @Autowired
        private AbonnementRepo abonnementRepository;

        @Override
        public Abonnement addAbonnement(Abonnement abonnement) {
            return abonnementRepository.save(abonnement);
        }

        @Override
        public void deleteAbonnement(Long id) {
            abonnementRepository.deleteById(id);
        }

        @Override
        public List<Abonnement> addListAbonnements(List<Abonnement> abonnements) {
            return abonnementRepository.saveAll(abonnements);
        }

        @Override
        public Abonnement updateAbonnement(Long id, Abonnement abonnementDetails) {
            Abonnement abonnement = abonnementRepository.findById(id).orElse(null);
            if (abonnement != null) {
                abonnement.setType(abonnementDetails.getType());
                abonnement.setPrix(abonnementDetails.getPrix());
                abonnement.setDateDebut(abonnementDetails.getDateDebut());
                abonnement.setDateFin(abonnementDetails.getDateFin());
                abonnement.setStatut(abonnementDetails.getStatut());
                return abonnementRepository.save(abonnement);
            }
            return null;
        }

        @Override
        public List<Abonnement> getAllAbonnements() {
            return abonnementRepository.findAll();
        }

        @Override
        public Abonnement getAbonnement(Long id) {
            return abonnementRepository.findById(id).orElse(null);
        }

        @Override
        public List<Abonnement> getAbonnementsByAdherent(UserEntity adherent) {
            return abonnementRepository.findByUser(adherent );
        }
}
