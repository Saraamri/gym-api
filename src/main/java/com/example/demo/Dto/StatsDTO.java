package com.example.demo.Dto;

import lombok.Data;

@Data
public class StatsDTO {

    private long adherents;
    private long coachs;
    private long abonnes;
    private long cours;
    private long avis;
    private double revenus;

    public StatsDTO(long adherents, long coachs,long abonnes, long cours, long avis, double revenus) {
        this.adherents = adherents;
        this.coachs = coachs;
        this.abonnes=abonnes;
        this.cours = cours;
        this.avis = avis;
        this.revenus = revenus;
    }


}
