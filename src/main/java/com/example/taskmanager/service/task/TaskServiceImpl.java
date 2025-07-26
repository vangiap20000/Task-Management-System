package com.example.taskmanager.service.task;

import com.example.taskmanager.repository.task.TaskRepository;
import org.springframework.stereotype.Service;
import com.example.taskmanager.model.Task;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<Task> listTask(int pageNumber, String searchValue, String sortBy, String sortValue) {
        int pageSize = 10;

        searchValue = "%" + searchValue + "%";

        if (searchValue == null || searchValue.isEmpty()) {
            searchValue = "%";
        } 

        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "id";
        }

        Sort sort = Sort.by(Sort.Direction.fromString(sortValue), sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        return taskRepository.findByTitleLike(searchValue, pageable);
    }

    public Boolean delete(Long id) {
        try {
            taskRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}