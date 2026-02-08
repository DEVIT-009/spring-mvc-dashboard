package com.pos.dashboardmvc.controllers;

import com.pos.dashboardmvc.models.Teacher;
import com.pos.dashboardmvc.services.TeacherService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/v1/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService){
        this.teacherService = teacherService;
    }

    @GetMapping({"/", ""})
    public String index(Model model){
        model.addAttribute("pageTitle", "Teachers Management");
        model.addAttribute("teachers", teacherService.listAll());

        return "contents/teachers/index";
    }

    @GetMapping("/create")
    public String formCreate(Model model){
        model.addAttribute("pageTitle", "Teachers Management");
        model.addAttribute("teacher", new Teacher());

        return "contents/teachers/create";
    }

    @PostMapping("/create")
    public String createTeacher(
        @Valid @ModelAttribute("teacher") Teacher formTeacher,
        BindingResult result,
        @RequestParam("image") MultipartFile image,
        RedirectAttributes redirectAttributes
    ){
        if (result.hasErrors()) {
            return "contents/teachers/create";
        }
        try{
            teacherService.create(formTeacher, image);
            redirectAttributes.addFlashAttribute("success", "Created teacher successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed create teacher!");
            throw new RuntimeException(e);
        }

        return "redirect:/admin/v1/teachers";
    }

    @GetMapping("/update/{id}")
    public String formUpdate(@PathVariable int id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id);

        model.addAttribute("teacher", teacher);
        model.addAttribute("pageTitle", "Edit Teacher");

        return "contents/teachers/update";
    }

    @PostMapping("/update/{id}")
    public String updateTeacher(
            @PathVariable int id,
            @ModelAttribute Teacher formTeacher,
            @RequestParam("image") MultipartFile image,
            RedirectAttributes redirectAttributes
    ) {
        try {
            teacherService.update(id, formTeacher, image);
            redirectAttributes.addFlashAttribute("success", "Teacher updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update teacher!");
        }

        return "redirect:/admin/v1/teachers/update/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteTeacher(
            @PathVariable int id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            teacherService.delete(id);
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Teacher deleted successfully!"
            );
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Teacher not found!"
            );
        }
        return "redirect:/admin/v1/teachers";
    }
}
