package com.example.taskmanager.repository.category;

import com.example.taskmanager.model.Category;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.taskmanager.repository.BaseRepository; 

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, BaseRepository<Category, Long>, CategoryRepositoryCustom {

}