package com.example.taskmanager.validators;

import com.example.taskmanager.repository.label.LabelRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class LabelIdsValidator implements ConstraintValidator<ValidLabelIds, Long[]> {

    @Autowired
    private LabelRepository labelRepository;

    @Override
    public boolean isValid(Long[] values, ConstraintValidatorContext context) {
        if (values == null || values.length == 0) {
            return true;
        }

        return Arrays.stream(values)
                .allMatch(id -> labelRepository.existsById(id));
    }
}