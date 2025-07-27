package com.example.taskmanager.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = FileValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFile {
    String message() default "Invalid file. Please ensure file type and size are correct.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}