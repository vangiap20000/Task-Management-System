package com.example.taskmanager.controller;

import com.example.taskmanager.model.Label;
import com.example.taskmanager.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin/labels")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("labels", labelService.findAll());
        model.addAttribute("pageTitle", "Danh sách nhãn");
        model.addAttribute("currentPath", "/admin/labels");
        return "layout/main";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("label", new Label());
        model.addAttribute("pageTitle", "Tạo nhãn mới");
        return "labels/form";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute Label label, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Tạo nhãn mới");
            return "labels/form";
        }
        if (labelService.existsByName(label.getName())) {
            result.rejectValue("name", "error.label", "Tên nhãn đã tồn tại");
            model.addAttribute("pageTitle", "Tạo nhãn mới");
            return "labels/form";
        }
        labelService.save(label);
        redirectAttributes.addFlashAttribute("success", "Tạo nhãn thành công!");
        return "redirect:/admin/labels";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Label> labelOpt = labelService.findById(id);
        if (labelOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy nhãn!");
            return "redirect:/admin/labels";
        }
        model.addAttribute("label", labelOpt.get());
        model.addAttribute("pageTitle", "Chỉnh sửa nhãn");
        return "labels/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable int id, @Valid @ModelAttribute Label label, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Chỉnh sửa nhãn");
            return "labels/form";
        }
        if (labelService.existsByName(label.getName()) && labelService.findById(id).map(l -> !l.getName().equalsIgnoreCase(label.getName())).orElse(false)) {
            result.rejectValue("name", "error.label", "Tên nhãn đã tồn tại");
            model.addAttribute("pageTitle", "Chỉnh sửa nhãn");
            return "labels/form";
        }
        label.setId(id);
        labelService.save(label);
        redirectAttributes.addFlashAttribute("success", "Cập nhật nhãn thành công!");
        return "redirect:/admin/labels";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {
        labelService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Xóa nhãn thành công!");
        return "redirect:/admin/labels";
    }
}