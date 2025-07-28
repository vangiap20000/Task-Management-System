package com.example.taskmanager.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Date;

public class MinCurrentDateValidator implements ConstraintValidator<ValidMinCurrentDate, Date> {

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Date currentDate = new Date();
        return !value.before(currentDate);
    }
}