package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import org.springframework.data.domain.Page;
import com.example.taskmanager.service.task.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/admin/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public String index(
        @RequestParam(name = "page", required = false, defaultValue="1") int page,
        @RequestParam(name = "search_value", required = false, defaultValue="") String searchValue,
        @RequestParam(name = "sort_by", required = false, defaultValue="") String sortBy,
        @RequestParam(name = "sort_value", required = false, defaultValue = "asc") String sortValue,
        Model model
    ) {
        
        Page<Task> tasks = taskService.listTask(page - 1, searchValue, sortBy, sortValue);

        model.addAttribute("contentPage", "tasks/list");
	    model.addAttribute("pageTitle", "List of Tasks");
	    model.addAttribute("currentPath", "/admin/tasks");
        model.addAttribute("customCssList", List.of("/css/task.css"));
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortValue", sortValue);
        model.addAttribute("tasks", tasks);
        model.addAttribute("currentPage", page);

        String paginationParams = "&search_value=" + searchValue + "&sort_by=" + sortBy + "&sort_value=" + sortValue;

        model.addAttribute("paginationParams", paginationParams);

	    return "layout/main";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            taskService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Task deleted successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error deleting task: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/admin/tasks"; 
    }
}
