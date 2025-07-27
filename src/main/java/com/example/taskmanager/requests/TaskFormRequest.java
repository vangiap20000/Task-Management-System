package com.example.taskmanager.requests;

import jakarta.validation.constraints.*;
// import java.util.Date;
// import org.springframework.web.multipart.MultipartFile; 
// import com.example.taskmanager.validators.ValidFile;


public class TaskFormRequest {
    // Max file 5MB
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    @NotNull(message = "Title is required")
    @Size(max = 255, message = "Title cannot be longer than 255 characters")
    private String title;

    @NotNull(message = "Title is required")
    private String description;

    // @NotNull(message = "Title is required")
    // @PastOrPresent(message = "Due date must be in the past or present")
    // private Date dueDate;

    // @ValidFile
    // private MultipartFile photo;

    // @Min(value = 0, message = "Status must be between 0 and 2")
    // @Max(value = 2, message = "Status must be between 0 and 2")
    // private Integer status;

    // @Min(value = 0, message = "Priority must be between 0 and 2")
    // @Max(value = 2, message = "Priority must be between 0 and 2")
    // private Integer priority;


    // private Integer categoryId;


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

    // public Date getDueDate() {
    //     return dueDate;
    // }

    // public void setDueDate(Date dueDate) {
    //     this.dueDate = dueDate;
    // }

    // public MultipartFile getPhoto() {
    //     return photo;
    // }

    // public void setPhoto(MultipartFile photo) {
    //     this.photo = photo;
    // }

    // public Integer getStatus() {
    //     return status;
    // }

    // public void setStatus(Integer status) {
    //     this.status = status;
    // }

    // public Integer getPriority() {
    //     return priority;
    // }

    // public void setPriority(Integer priority) {
    //     this.priority = priority;
    // }

    // public Integer getCategoryId() {
    //     return categoryId;
    // }

    // public void setCategoryId(Integer categoryId) {
    //     this.categoryId = categoryId;
    // }
}
