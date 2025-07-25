package com.example.taskmanager.repository.user;

import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.BaseRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepositoryImpl extends BaseRepositoryImpl<User, Long> implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    @Transactional
    public void customMethod() {
        System.out.println("Hello from UserRepositoryImpl!");
    }
}
