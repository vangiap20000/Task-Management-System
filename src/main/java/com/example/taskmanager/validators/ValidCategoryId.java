package com.example.taskmanager.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CategoryIdValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCategoryId {
    String message() default "Invalid category selected";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
