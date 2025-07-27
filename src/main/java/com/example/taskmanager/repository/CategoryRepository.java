package com.example.taskmanager.repository;

import com.example.taskmanager.model.Category;
import com.example.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
    List<Category> findByUser(User user);
    
    List<Category> findByUserAndNameContainingIgnoreCase(User user, String name);
    
    boolean existsByUserAndNameIgnoreCase(User user, String name);
    
    @Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.user = :user AND LOWER(c.name) = LOWER(:name) AND c.id != :id")
    boolean existsByUserAndNameIgnoreCaseAndIdNot(@Param("user") User user, @Param("name") String name, @Param("id") int id);
    
    Optional<Category> findByIdAndUser(int id, User user);
} 