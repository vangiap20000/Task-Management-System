package com.example.taskmanager.service;

import com.example.taskmanager.model.Label;
import com.example.taskmanager.repository.label.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

    public List<Label> findAll() {
        return labelRepository.findAll();
    }

    public Optional<Label> findById(int id) {
        return labelRepository.findById(id);
    }

    public Label save(Label label) {
        return labelRepository.save(label);
    }

    public void deleteById(int id) {
        labelRepository.deleteById(id);
    }

    public boolean existsByName(String name) {
        return labelRepository.existsByNameIgnoreCase(name);
    }

    public List<Label> findByNameContainingIgnoreCase(String name) {
        return labelRepository.findByNameContainingIgnoreCase(name);
    }
}