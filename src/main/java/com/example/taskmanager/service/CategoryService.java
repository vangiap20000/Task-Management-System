package com.example.taskmanager.service;

import com.example.taskmanager.model.Category;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAllByUser(User user) {
        return categoryRepository.findByUser(user);
    }

    public Page<Category> findAllByUserWithPagination(User user, Pageable pageable) {
        List<Category> allCategories = categoryRepository.findByUser(user);
        
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allCategories.size());
        
        if (start > allCategories.size()) {
            return Page.empty(pageable);
        }
        
        List<Category> pageContent = allCategories.subList(start, end);
        
        return new org.springframework.data.domain.PageImpl<>(
            pageContent, 
            pageable, 
            allCategories.size()
        );
    }

    public List<Category> findByUserAndKeyword(User user, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return categoryRepository.findByUser(user);
        }
        return categoryRepository.findByUserAndNameContainingIgnoreCase(user, keyword.trim());
    }

    public Optional<Category> findByIdAndUser(int id, User user) {
        return categoryRepository.findByIdAndUser(id, user);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }

    public boolean existsByNameAndUser(String name, User user) {
        return categoryRepository.existsByUserAndNameIgnoreCase(user, name);
    }

    public boolean existsByNameAndUserAndIdNot(String name, User user, int id) {
        return categoryRepository.existsByUserAndNameIgnoreCaseAndIdNot(user, name, id);
    }

    public List<Category> findByNameContainingIgnoreCase(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }
}