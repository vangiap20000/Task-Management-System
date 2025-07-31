package com.example.taskmanager.validators;

import com.example.taskmanager.repository.label.LabelRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LabelIdsValidator implements ConstraintValidator<ValidLabelIds, List<Integer>> {

    @Autowired
    private LabelRepository labelRepository;

    @Override
    public boolean isValid(List<Integer> values, ConstraintValidatorContext context) {
        if (values == null || values.size() == 0) {
            return true;
        }

        return values.stream()
                .allMatch(id -> labelRepository.existsById(id));
    }
}