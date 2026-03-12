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
    public String createStudent(
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
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create student!");
            return "redirect:/admin/v1/students/create";
        }

        return "redirect:/admin/v1/students";
    }

    @GetMapping("/update/{id}")
    public String formUpdate(@PathVariable int id, Model model) {
        Student student = studentService.getStudentById(id);

        model.addAttribute("student", student);
        model.addAttribute("pageTitle", "Edit Student");

        return "contents/students/update";
    }

    @GetMapping("/detail/{id}")
    public String formDetail(@PathVariable int id, Model model) {
        Student student = studentService.getStudentById(id);

        model.addAttribute("student", student);
        model.addAttribute("pageTitle", "Detail Student");

        return "contents/students/detail";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(
            @PathVariable int id,
            @Valid @ModelAttribute("student") Student formStudent,
            BindingResult result,
            @RequestParam("image") MultipartFile image,
            RedirectAttributes redirectAttributes
    ) {
        if(result.hasErrors()){
            return "redirect:/admin/v1/students/update/" + id;
        }
        try {
            studentService.update(id, formStudent, image);
            redirectAttributes.addFlashAttribute("success", "Student updated successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update student!");
        }

        return "redirect:/admin/v1/students/update/" + id;
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(
            @PathVariable int id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            studentService.delete(id);
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Student deleted successfully!"
            );
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Student not found!"
            );
        }
        return "redirect:/admin/v1/students";
    }
}
