package com.example.taskmanager.repository.category;

import com.example.taskmanager.model.Category;
import com.example.taskmanager.repository.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;

@Repository
public class CategoryRepositoryImpl extends BaseRepositoryImpl<Category, Long> implements CategoryRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public CategoryRepositoryImpl() {
        super(Category.class);
    }

    @Override
    @Transactional
    public void customMethod() {
        System.out.println("Hello from CategoryRepositoryImpl!");
    }
}