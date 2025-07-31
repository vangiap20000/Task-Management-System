package com.example.taskmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("name", "Word");
        return "index";
    }

	@RequestMapping("/admin/common")
    public String common(Model model) {
		model.addAttribute("dropdownOptions", Map.of(
			"vn", "Vietnam",
			"jp", "Japan",
			"us", "USA"
		));
        model.addAttribute("contentPage", "common");
	    model.addAttribute("pageTitle", "Common");
	    model.addAttribute("currentPath", "/admin/common");
	    return "layout/main";
    }
}
