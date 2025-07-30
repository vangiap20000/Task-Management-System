package com.example.taskmanager.requests;

import jakarta.validation.constraints.*;
import com.example.taskmanager.utils.FileUtils;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile; 
import com.example.taskmanager.validators.ValidFile;
import com.example.taskmanager.validators.ValidCategoryId;
import com.example.taskmanager.validators.ValidLabelIds;
import com.example.taskmanager.validators.ValidMinCurrentDate;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.Label;
import java.util.stream.Collectors;

public class TaskFormRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot be longer than 255 characters")
    private String title;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Due Date is required")
    @ValidMinCurrentDate
    private LocalDate dueDate;

    @ValidFile
    private MultipartFile photo;

    private String photoUrl;

    @NotNull(message = "Status is required")
    @Min(value = 0, message = "Status must be between 0 and 2")
    @Max(value = 2, message = "Status must be between 0 and 2")
    private Integer status;

    @NotNull(message = "Priority is required")
    @Min(value = 0, message = "Priority must be between 0 and 2")
    @Max(value = 2, message = "Priority must be between 0 and 2")
    private Integer priority;

    @ValidCategoryId
    @NotNull(message = "Category is required")
    private Long categoryId;

    @ValidLabelIds
    private Long[] labelId;

    private Integer deleteFile;

    public TaskFormRequest() {

    }

    public TaskFormRequest(Task task) {
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.dueDate = task.getDueDate();
        if(task.getPhoto() != null) {
            this.photoUrl = FileUtils.buildPhotoUrl(task.getPhoto());
        }
        
        this.status = task.getStatus();
        this.priority = task.getPriority();

        if(task.getCategory() != null) {
            this.categoryId = (long) task.getCategory().getId(); 
        }

        if(task.getLabels() != null) {
            Long[] labelIds = task.getLabels().stream()
                .map(Label::getId)
                .map(id -> Long.valueOf(id))
                .toArray(Long[]::new);
            this.labelId = labelIds;
        }
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long[] getLabelId() {
        return labelId;
    }

    public void setLabelId(Long[] labelId) {
        this.labelId = labelId;
    }

    public Integer getDeleteFile() {
        return deleteFile;
    }

    public void setDeleteFile(Integer deleteFile) {
        this.deleteFile = deleteFile;
    }
}
