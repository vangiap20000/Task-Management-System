package com.example.taskmanager.service.category;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.taskmanager.model.Category;

@Service
public interface CategoryService {
    public List<Category> getAll();
}
