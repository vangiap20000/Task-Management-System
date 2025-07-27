package com.example.taskmanager.validators;

import com.example.taskmanager.repository.category.CategoryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryIdValidator implements ConstraintValidator<ValidCategoryId, Long> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return categoryRepository.existsById(value);
    }
}