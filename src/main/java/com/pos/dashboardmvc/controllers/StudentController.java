package com.pos.dashboardmvc.controllers;

import com.pos.dashboardmvc.models.Student;
import com.pos.dashboardmvc.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping({"", "/"})
    public String index(Model model) {
        model.addAttribute("pageTitle", "Manage Students");
        model.addAttribute("students", studentService.listAll());

        return "contents/students/index";
    }

    @GetMapping("/create")
    public String formCreate(Model model) {
        model.addAttribute("pageTitle", "Manage Students");
        model.addAttribute("student", new Student());

        return "contents/students/create";
    }

    @PostMapping("/create")
    public String CreateStudent(
            @Valid @ModelAttribute("student") Student formStudent,
            BindingResult result,
            @RequestParam("image") MultipartFile image,
            RedirectAttributes redirectAttributes
    ) {
        if(result.hasErrors()){
            return "contents/students/create";
        }
        try {
            studentService.create(formStudent, image);
            redirectAttributes.addFlashAttribute("success", "Student created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create student!");
            throw new RuntimeException(e);
        }

        return "redirect:/admin/v1/students";
    }
}
