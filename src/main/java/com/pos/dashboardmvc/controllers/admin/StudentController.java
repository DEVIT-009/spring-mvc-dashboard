package com.pos.dashboardmvc.controllers.admin;

import com.pos.dashboardmvc.models.Student;
import com.pos.dashboardmvc.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('STUDENT_VIEW')")
    @GetMapping({"", "/"})
    public String index(Model model) {
        model.addAttribute("pageTitle", "Manage Students");
        model.addAttribute("students", studentService.listAll());

        return "admin/contents/students/index";
    }

    @PreAuthorize("hasAuthority('STUDENT_VIEW')")
    @GetMapping("/create")
    public String formCreate(Model model) {
        model.addAttribute("pageTitle", "Manage Students");
        model.addAttribute("student", new Student());

        return "admin/contents/students/create";
    }

    @PreAuthorize("hasAuthority('STUDENT_CREATE')")
    @PostMapping("/create")
    public String createStudent(
            @Valid @ModelAttribute("student") Student formStudent,
            BindingResult result,
            @RequestParam("image") MultipartFile image,
            RedirectAttributes redirectAttributes
    ) {
        if(result.hasErrors()){
            return "admin/contents/students/create";
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

    @PreAuthorize("hasAuthority('STUDENT_VIEW')")
    @GetMapping("/update/{id}")
    public String formUpdate(@PathVariable int id, Model model) {
        Student student = studentService.getStudentById(id);

        model.addAttribute("student", student);
        model.addAttribute("pageTitle", "Edit Student");

        return "admin/contents/students/update";
    }

    @PreAuthorize("hasAuthority('STUDENT_VIEW')")
    @GetMapping("/detail/{id}")
    public String formDetail(@PathVariable int id, Model model) {
        Student student = studentService.getStudentById(id);

        model.addAttribute("student", student);
        model.addAttribute("pageTitle", "Detail Student");

        return "admin/contents/students/detail";
    }

    @PreAuthorize("hasAuthority('STUDENT_UPDATE')")
    @PutMapping("/update/{id}")
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

    @PreAuthorize("hasAuthority('STUDENT_DELETE')")
    @DeleteMapping("/delete/{id}")
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
