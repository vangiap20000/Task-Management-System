package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.taskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/admin/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService

    @GetMapping
    public String index(
        @RequestParam(name = "search_value", required = false, defaultValue="") String searchValue,
        @RequestParam(name = "sort_by", required = false) String sortBy,
        @RequestParam(name = "sort_value", required = false, defaultValue = "asc") String sortValue,
        Model model
    ) {
        model.addAttribute("contentPage", "tasks/list");
	    model.addAttribute("pageTitle", "List of Tasks");
	    model.addAttribute("currentPath", "/admin/tasks");
        model.addAttribute("customCssList", List.of("/css/task.css"));
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortValue", sortValue);

	    return "layout/main";
    }
}
