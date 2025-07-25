package com.example.taskmanager.service.task;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.task.TaskRepository;
import com.example.taskmanager.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl extends BaseServiceImpl<Task, Long> implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        super(taskRepository);
        this.taskRepository = taskRepository;
    }
}