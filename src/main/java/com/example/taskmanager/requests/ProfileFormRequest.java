package com.example.taskmanager.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProfileFormRequest {

    @Size(max = 12, message = "Phone number up to 12 characters")
    @Pattern(regexp = "\\d{10,12}", message = "Invalid phone number")
    private String phone;

    @NotEmpty(message = "Name cannot be blank")
    private String name;

    // Không annotation, cho phép null
    private String password;

    private String confirmPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}