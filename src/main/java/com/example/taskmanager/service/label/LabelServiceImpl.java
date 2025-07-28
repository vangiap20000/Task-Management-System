package com.example.taskmanager.service.label;

import com.example.taskmanager.repository.label.LabelRepository;
import org.springframework.stereotype.Service;
import com.example.taskmanager.model.Label;
import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;

    public LabelServiceImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public List<Label> getAll() {
        return labelRepository.findAll();
    }
}