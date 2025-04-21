package com.example.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AbonnementDateValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAbonnementDates {
    String message() default "La date de fin doit être après la date de début";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

