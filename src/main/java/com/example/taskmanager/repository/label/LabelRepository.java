package com.example.taskmanager.repository.label;

import com.example.taskmanager.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LabelRepository extends JpaRepository<Label, Integer> {
    boolean existsByNameIgnoreCase(String name);
    Optional<Label> findById(int id);
    List<Label> findByNameContainingIgnoreCase(String name);
}