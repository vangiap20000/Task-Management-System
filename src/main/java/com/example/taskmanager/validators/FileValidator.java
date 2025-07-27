package com.example.taskmanager.validators;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    @Override
    public void initialize(ValidFile constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file.getSize() > MAX_FILE_SIZE) {
            context.buildConstraintViolationWithTemplate("File is too large. Maximum size allowed is 5MB.")
                   .addConstraintViolation();
            return false;
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            context.buildConstraintViolationWithTemplate("Invalid file type. Only image files are allowed.")
                   .addConstraintViolation();
            return false;
        }

        return true;
    }
}
