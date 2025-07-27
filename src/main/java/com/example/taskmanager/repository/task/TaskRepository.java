package com.example.taskmanager.repository.task;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.taskmanager.repository.BaseRepository; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;  

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, BaseRepository<Task, Long>, TaskRepositoryCustom {
    Page<Task> findByUserAndTitleLike(User user, String title, Pageable pageable);
}
