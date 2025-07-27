package com.example.taskmanager.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LabelIdsValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLabelIds {
    String message() default "Invalid labels selected";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}