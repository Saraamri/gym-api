package com.example.demo.services;

import com.example.demo.Dto.StatsDTO;
import com.example.demo.entities.RoleName;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
    @Service
    public class StatsService {

        private final UserRepo userRepository;
        private final AbonnementRepo abonnementRepository;
        private final CoursRepo coursRepository;
        private final CommentRepo avisRepository;
        private final PaiementRepo paiementRepository;

        public StatsService(UserRepo userRepository,
                            AbonnementRepo abonnementRepository,
                            CoursRepo coursRepository,
                            CommentRepo avisRepository,
                            PaiementRepo paiementRepository) {
            this.userRepository = userRepository;
            this.abonnementRepository = abonnementRepository;
            this.coursRepository = coursRepository;
            this.avisRepository = avisRepository;
            this.paiementRepository = paiementRepository;
        }

        public StatsDTO getStats() {
            int adherents = userRepository.countByRole(RoleName.ADHERENT);
            int coachs = userRepository.countByRole(RoleName.COACH);
            long abonnes = abonnementRepository.count();
            long cours = coursRepository.count();
            long avis = avisRepository.countByActiveTrue();
            double revenus = paiementRepository.sumPaiements();

            return new StatsDTO(adherents, coachs, abonnes, cours, avis, revenus);
        }
    }

