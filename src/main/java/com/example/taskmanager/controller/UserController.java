package com.example.taskmanager.controller;

import com.example.taskmanager.model.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.taskmanager.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping
	public String listUsers(Model model) {
		model.addAttribute("contentPage", "users/list");
        model.addAttribute("pageTitle", "User list");
        model.addAttribute("currentPath", "/admin/users");
		return "layout/main";
	}

	@GetMapping("/create")
	public String createForm(Model model) {
        model.addAttribute("contentPage", "users/form");
        model.addAttribute("pageTitle", "Create new user");
        model.addAttribute("currentPath", "/admin/users");
        return "layout/main";
	}
}
