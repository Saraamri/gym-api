package com.example.demo.validation;

import com.example.demo.entities.Abonnement;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AbonnementDateValidator implements ConstraintValidator<ValidAbonnementDates, Abonnement> {

    @Override
    public boolean isValid(Abonnement abonnement, ConstraintValidatorContext context) {
        if (abonnement == null || abonnement.getDateDebut() == null || abonnement.getDateFin() == null) {
            return true; // Les autres validations s'en chargent (ex : @NotNull)
        }

        boolean isValid = abonnement.getDateFin().isAfter(abonnement.getDateDebut());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("La date de fin doit être après la date de début")
                    .addPropertyNode("dateFin") // Lier l'erreur à l'attribut dateFin
                    .addConstraintViolation();
        }

        return isValid;
    }
}
