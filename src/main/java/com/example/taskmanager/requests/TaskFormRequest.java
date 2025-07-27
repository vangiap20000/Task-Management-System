package com.example.taskmanager.requests;

import jakarta.validation.constraints.*;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile; 
import com.example.taskmanager.validators.ValidFile;
import com.example.taskmanager.validators.ValidCategoryId;
import com.example.taskmanager.validators.ValidLabelIds;


public class TaskFormRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot be longer than 255 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Due Date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Due date must be in the past or present")
    private Date dueDate;

    @ValidFile
    private MultipartFile photo;

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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
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
}
