package com.example.taskmanager.repository.label;

import com.example.taskmanager.model.Label;
import org.springframework.stereotype.Repository;
import com.example.taskmanager.repository.BaseRepository; 
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long>, BaseRepository<Label, Long>, LabelRepositoryCustom {

}