package com.example.taskmanager.repository.task;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;

@Repository
public class TaskRepositoryImpl extends BaseRepositoryImpl<Task, Long> implements TaskRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public TaskRepositoryImpl() {
        super(Task.class);
    }

    @Override
    @Transactional
    public void customMethod() {
        System.out.println("Hello from TaskRepositoryImpl!");
    }

}
