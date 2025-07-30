package com.example.taskmanager.repository.label;

import com.example.taskmanager.model.Label;
import com.example.taskmanager.repository.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;

@Repository
public class LabelRepositoryImpl extends BaseRepositoryImpl<Label, Long> implements LabelRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public LabelRepositoryImpl() {
        super(Label.class);
    }

    @Override
    @Transactional
    public void customMethod() {
        System.out.println("Hello from LabelRepositoryImpl!");
    }

}
