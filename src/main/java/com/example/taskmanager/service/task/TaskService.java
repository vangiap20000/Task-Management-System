package com.example.taskmanager.service.task;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.taskmanager.model.Task;
import org.springframework.data.domain.Page;
import com.example.taskmanager.requests.TaskFormRequest;

@Service
public interface TaskService {
    public Page<Task> listTask(int pageNumber, String searchValue, String sortBy, String sortValue);

    public Boolean delete(Long id);

    public Boolean store(TaskFormRequest taskFormRequest);

    public Task detail(Long id);

    public Boolean update(Long id, TaskFormRequest taskFormRequest);
}
