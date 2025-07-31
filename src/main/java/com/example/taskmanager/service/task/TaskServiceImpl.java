package com.example.taskmanager.service.task;

import com.example.taskmanager.repository.task.TaskRepository;
import com.example.taskmanager.repository.label.LabelRepository;
import com.example.taskmanager.repository.category.CategoryRepository;
import org.springframework.stereotype.Service;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.model.Label;
import com.example.taskmanager.model.Category;
import com.example.taskmanager.model.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.taskmanager.requests.TaskFormRequest;
import org.springframework.web.multipart.MultipartFile;
import jakarta.persistence.EntityNotFoundException;
import com.example.taskmanager.utils.FileUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final String UPLOAD_DIR = "uploads/";
    private final Integer IS_DELETE_FILE = 1;

    private final TaskRepository taskRepository;
    private final LabelRepository labelRepository;
    private final CategoryRepository categoryRepository;

    public TaskServiceImpl(
        TaskRepository taskRepository,
        CategoryRepository categoryRepository,
        LabelRepository labelRepository
    ) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.labelRepository= labelRepository;
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
        User currentUser = this.currentUser();

        return taskRepository.findByUserAndTitleLike(currentUser, searchValue, pageable);
    }

    public Boolean delete(Long id) {
        try {
            Task task = this.detail(id);
            if(task.getTitle() == null) {
                return false;
            }

            if (task.getPhoto() != null) {
                FileUtils.deleteFileIfExistsNio(task.getPhoto());
            }

            taskRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean store(TaskFormRequest taskFormRequest) {
        try {
            Category category = this.getCategory(taskFormRequest.getCategoryId());

            List<Integer> labelIds = taskFormRequest.getLabelId();
            Set<Label> labels = new HashSet<>(labelRepository.findAllById(labelIds));

            Task task = new Task();

            User currentUser = this.currentUser();
            task.setUser(currentUser);

            task.setTitle(taskFormRequest.getTitle());
            task.setDescription(taskFormRequest.getDescription());
            task.setDueDate(taskFormRequest.getDueDate());

            MultipartFile photo = taskFormRequest.getPhoto();
            if(!photo.isEmpty()) {
                task.setPhoto(FileUtils.uploadFile(photo, UPLOAD_DIR));
            }            

            task.setStatus(taskFormRequest.getStatus());
            task.setPriority(taskFormRequest.getPriority());
            task.setCategory(category);
            task.setLabels(labels);

            taskRepository.save(task);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean update(Long id, TaskFormRequest taskFormRequest) {

        Task task = this.detail(id);
        if(task.getTitle() == null) {
            return false;
        }

        try {
            if(
                task.getPhoto() != null 
                && taskFormRequest.getDeleteFile() == IS_DELETE_FILE
            ) {
                FileUtils.deleteFileIfExistsNio(task.getPhoto());
            }

            Category category = this.getCategory(taskFormRequest.getCategoryId());

            List<Integer> labelIds = taskFormRequest.getLabelId();
            Set<Label> labels = new HashSet<>(labelRepository.findAllById(labelIds));

            task.setTitle(taskFormRequest.getTitle());
            task.setDescription(taskFormRequest.getDescription());
            task.setDueDate(taskFormRequest.getDueDate());

            MultipartFile photo = taskFormRequest.getPhoto();
            if(!photo.isEmpty()) {
                task.setPhoto(FileUtils.uploadFile(photo, UPLOAD_DIR));
            } 
            
            if(photo.isEmpty() && taskFormRequest.getDeleteFile() == IS_DELETE_FILE) {
                task.setPhoto(null);
            }

            task.setStatus(taskFormRequest.getStatus());
            task.setPriority(taskFormRequest.getPriority());
            task.setCategory(category);
            task.setLabels(labels);

            taskRepository.save(task);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Task detail(Long id) {
        User currentUser = this.currentUser();

        Optional<Task> task =  taskRepository.findFirstByUserAndId(currentUser, id);

        return task.orElseThrow(() -> new EntityNotFoundException("Task with ID " + id + " not found"));
    };

    protected User currentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUserDetails = (CustomUserDetails) auth.getPrincipal();
        User currentUser = currentUserDetails.getUser();

        return currentUser;
    }

    protected Category getCategory(Integer categoryId) {
        return categoryRepository.findById(categoryId).get();
    }
}