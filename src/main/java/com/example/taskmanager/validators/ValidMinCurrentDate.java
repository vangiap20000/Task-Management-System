package com.example.taskmanager.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinCurrentDateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMinCurrentDate {
    String message() default "Date must be the current date or after.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}