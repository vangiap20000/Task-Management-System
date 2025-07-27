package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import org.springframework.data.domain.Page;
import com.example.taskmanager.service.task.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import com.example.taskmanager.requests.TaskFormRequest;
import org.springframework.web.bind.annotation.ModelAttribute;

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

    @GetMapping("/create")
    public String create(Model model, TaskFormRequest taskFormRequest) {
        model.addAttribute("taskFormRequest", taskFormRequest);

        model.addAttribute("contentPage", "tasks/create");
	    model.addAttribute("pageTitle", "Create new Task");
	    model.addAttribute("currentPath", "/admin/tasks/create");
        model.addAttribute("customCssList", List.of(
            "https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css",
            "https://cdn.jsdelivr.net/gh/erimicel/select2-tailwindcss-theme/dist/select2-tailwindcss-theme-plain.min.css",
            "/css/task.css"
        ));

        model.addAttribute("customJsList", List.of(
            "https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"
        ));

	    return "layout/main";
    }


    @PostMapping("/create")
    public String store(@Valid TaskFormRequest taskFormRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("taskFormRequest", taskFormRequest);

            return this.create(model, taskFormRequest);
        }

        return "redirect:/admin/tasks"; 
    }
}
