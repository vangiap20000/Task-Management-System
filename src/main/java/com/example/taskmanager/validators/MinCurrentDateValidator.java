package com.example.taskmanager.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class MinCurrentDateValidator implements ConstraintValidator<ValidMinCurrentDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        LocalDate currentDate = LocalDate.now();
        return !value.isBefore(currentDate);
    }
}