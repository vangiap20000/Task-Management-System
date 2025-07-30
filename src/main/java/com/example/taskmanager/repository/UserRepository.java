package com.example.taskmanager.repository;

import com.example.taskmanager.model.User;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
	Page<User> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    boolean existsByEmail(String email);
}
