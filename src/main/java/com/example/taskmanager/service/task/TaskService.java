package com.example.taskmanager.service.task;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.taskmanager.model.Task;
import org.springframework.data.domain.Page;

@Service
public interface TaskService {
    public Page<Task> listTask(int pageNumber, String searchValue, String sortBy, String sortValue);

    Boolean delete(Long id);
}
