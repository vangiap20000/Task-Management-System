package com.example.taskmanager.service.task;

import com.example.taskmanager.service.BaseService;
import org.springframework.stereotype.Service;
import com.example.taskmanager.model.Task;

@Service
public interface TaskService extends BaseService<Task, Long> {
}
