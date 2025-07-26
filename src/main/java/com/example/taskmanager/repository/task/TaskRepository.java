package com.example.taskmanager.repository.task;

import com.example.taskmanager.model.Task;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.taskmanager.repository.BaseRepository;   

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, BaseRepository<Task, Long>, TaskRepositoryCustom {

}
