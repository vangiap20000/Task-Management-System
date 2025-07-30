package com.example.taskmanager.service.category;

import com.example.taskmanager.repository.category.CategoryRepository;
import org.springframework.stereotype.Service;
import com.example.taskmanager.model.Category;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}