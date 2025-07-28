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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class TaskServiceImpl implements TaskService {

    private final String UPLOAD_DIR = "uploads/";

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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUserDetails = (CustomUserDetails) auth.getPrincipal();
        User currentUser = currentUserDetails.getUser();

        return taskRepository.findByUserAndTitleLike(currentUser, searchValue, pageable);
    }

    public Boolean delete(Long id) {
        try {
            taskRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean store(TaskFormRequest taskFormRequest) {
        try {
            Category categories = categoryRepository.findById(taskFormRequest.getCategoryId()).get();

            List<Long> labelIds = Arrays.asList(taskFormRequest.getLabelId());
            Set<Label> labels = new HashSet<>(labelRepository.findAllById(labelIds));

            Task task = new Task();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails currentUserDetails = (CustomUserDetails) auth.getPrincipal();
            User currentUser = currentUserDetails.getUser();
            task.setUser(currentUser);

            task.setTitle(taskFormRequest.getTitle());
            task.setDescription(taskFormRequest.getDescription());
            task.setDueDate(taskFormRequest.getDueDate());

            MultipartFile photo = taskFormRequest.getPhoto();
            if(!photo.isEmpty()) {
                task.setPhoto(this.uploadFile(photo));
            }            

            task.setStatus(taskFormRequest.getStatus());
            task.setPriority(taskFormRequest.getPriority());
            task.setCategory(categories);
            task.setLabels(labels);

            taskRepository.save(task);

            return true;
        } catch (Exception e) {
            return false;
        }
    }


    protected String uploadFile(MultipartFile file) throws IOException {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();

        } catch (IOException e) {
            throw new IOException("Failed to upload file: " + e.getMessage());
        }
    }
}