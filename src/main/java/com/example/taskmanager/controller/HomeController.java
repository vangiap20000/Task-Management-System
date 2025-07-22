package com.example.taskmanager.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("pageTitle", "Trang chủ");
		model.addAttribute("contentTemplate", "index"); // tên file template
		model.addAttribute("dropdownOptions", Map.of(
			"vn", "Vietnam",
			"jp", "Japan",
			"us", "USA"
		));
		return "layout/base";
	}

}