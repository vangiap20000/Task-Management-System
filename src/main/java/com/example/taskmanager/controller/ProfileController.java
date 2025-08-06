package com.example.taskmanager.controller;

import com.example.taskmanager.model.User;
import com.example.taskmanager.requests.ProfileFormRequest;
import com.example.taskmanager.service.UserService;
import com.example.taskmanager.model.CustomUserDetails;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String showProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();

        // Nếu chưa có profileFormRequest (từ redirect), tạo mới từ user
        if (!model.containsAttribute("profileFormRequest")) {
            ProfileFormRequest form = new ProfileFormRequest();
            form.setName(user.getName());
            form.setPhone(user.getPhone());
            model.addAttribute("profileFormRequest", form);
        }

        model.addAttribute("user", user);
        model.addAttribute("contentPage", "profile");
        model.addAttribute("pageTitle", "Profile");
        model.addAttribute("currentPath", "/admin/profile");
        return "layout/main";
    }

    @PostMapping("/profile/update")
    public String updateProfile(
            @Valid ProfileFormRequest profileFormRequest,
            BindingResult bindingResult,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            Model model,
            RedirectAttributes redirectAttributes) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User currentUser = userDetails.getUser();

        if (profileFormRequest.getPassword() != null && !profileFormRequest.getPassword().isEmpty()) {
            currentUser.setPassword(userService.encodePassword(profileFormRequest.getPassword()));
        }
        // Không set password nếu không nhập

        // Validate password trùng confirmPassword nếu có nhập password
        if (profileFormRequest.getPassword() != null && !profileFormRequest.getPassword().isEmpty()) {
            if (!profileFormRequest.getPassword().equals(profileFormRequest.getConfirmPassword())) {
                bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Confirmation password does not match");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", currentUser);
            model.addAttribute("profileFormRequest", profileFormRequest);
            model.addAttribute("contentPage", "profile");
            model.addAttribute("pageTitle", "Profile");
            model.addAttribute("currentPath", "/admin/profile");
            return "layout/main";
        }

        try {
            currentUser.setName(profileFormRequest.getName());
            currentUser.setPhone(profileFormRequest.getPhone());
            // Xử lý ảnh đại diện
            if (imageFile != null && !imageFile.isEmpty()) {
                String filename = userService.saveAvatarFile(imageFile, currentUser.getAvatar());
                if (filename != null) {
                    currentUser.setAvatar("users/" + filename);
                }
            }
            userService.saveProfile(currentUser);
            redirectAttributes.addFlashAttribute("success", "Information updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred while updating information!");
            e.printStackTrace();
        }

        return "redirect:/admin/profile";
    }
}
