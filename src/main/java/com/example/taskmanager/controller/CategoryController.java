package com.example.taskmanager.controller;

import com.example.taskmanager.model.Category;
import com.example.taskmanager.model.User;
import com.example.taskmanager.model.CustomUserDetails;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.taskmanager.service.CategoryService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

    @GetMapping
    public String listCategories(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {

        User currentUser = getCurrentUser();
        
        if (currentUser == null) {
            return "redirect:/login";
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Category> categoryPage;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            List<Category> categories = categoryService.findByUserAndKeyword(currentUser, keyword);

            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), categories.size());
            
            if (start > categories.size()) {
                categoryPage = Page.empty(pageable);
            } else {
                List<Category> pageContent = categories.subList(start, end);
                categoryPage = new org.springframework.data.domain.PageImpl<>(
                    pageContent, pageable, categories.size()
                );
            }
        } else {
            categoryPage = categoryService.findAllByUserWithPagination(currentUser, pageable);
        }

        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        model.addAttribute("totalItems", categoryPage.getTotalElements());
        model.addAttribute("size", size);
        model.addAttribute("keyword", keyword);
        model.addAttribute("contentPage", "categories/list");
        model.addAttribute("pageTitle", "Danh sách danh mục");
        model.addAttribute("currentPath", "/admin/categories");
        return "layout/main";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("category", new Category());
        model.addAttribute("contentPage", "categories/form");
        model.addAttribute("pageTitle", "Tạo danh mục mới");
        model.addAttribute("currentPath", "/admin/categories");
        return "layout/main";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute Category category, 
                        BindingResult result, 
                        RedirectAttributes redirectAttributes,
                        Model model) {
        
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            model.addAttribute("contentPage", "categories/form");
            model.addAttribute("pageTitle", "Tạo danh mục mới");
            model.addAttribute("currentPath", "/admin/categories");
            return "layout/main";
        }

        if (categoryService.existsByNameAndUser(category.getName(), currentUser)) {
            result.rejectValue("name", "error.category", "Tên danh mục đã tồn tại");
            model.addAttribute("contentPage", "categories/form");
            model.addAttribute("pageTitle", "Tạo danh mục mới");
            model.addAttribute("currentPath", "/admin/categories");
            return "layout/main";
        }

        category.setUser(currentUser);
        categoryService.save(category);
        
        redirectAttributes.addFlashAttribute("success", "Tạo danh mục thành công!");
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        Optional<Category> categoryOpt = categoryService.findByIdAndUser(id, currentUser);
        if (categoryOpt.isEmpty()) {
            return "redirect:/admin/categories";
        }

        model.addAttribute("category", categoryOpt.get());
        model.addAttribute("contentPage", "categories/form");
        model.addAttribute("pageTitle", "Chỉnh sửa danh mục");
        model.addAttribute("currentPath", "/admin/categories");
        return "layout/main";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable int id,
                      @Valid @ModelAttribute Category category,
                      BindingResult result,
                      RedirectAttributes redirectAttributes,
                      Model model) {
        
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            model.addAttribute("contentPage", "categories/form");
            model.addAttribute("pageTitle", "Chỉnh sửa danh mục");
            model.addAttribute("currentPath", "/admin/categories");
            return "layout/main";
        }

        Optional<Category> existingCategoryOpt = categoryService.findByIdAndUser(id, currentUser);
        if (existingCategoryOpt.isEmpty()) {
            return "redirect:/admin/categories";
        }

        Category existingCategory = existingCategoryOpt.get();
        
        if (categoryService.existsByNameAndUserAndIdNot(category.getName(), currentUser, id)) {
            result.rejectValue("name", "error.category", "Tên danh mục đã tồn tại");
            model.addAttribute("contentPage", "categories/form");
            model.addAttribute("pageTitle", "Chỉnh sửa danh mục");
            model.addAttribute("currentPath", "/admin/categories");
            return "layout/main";
        }

        existingCategory.setName(category.getName());
        categoryService.save(existingCategory);
        
        redirectAttributes.addFlashAttribute("success", "Cập nhật danh mục thành công!");
        return "redirect:/admin/categories";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        Optional<Category> categoryOpt = categoryService.findByIdAndUser(id, currentUser);
        if (categoryOpt.isPresent()) {
            categoryService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Xóa danh mục thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy danh mục!");
        }
        
        return "redirect:/admin/categories";
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails currentUserDetails = (CustomUserDetails) auth.getPrincipal();
            return currentUserDetails.getUser();
        }
        return null;
    }
}
