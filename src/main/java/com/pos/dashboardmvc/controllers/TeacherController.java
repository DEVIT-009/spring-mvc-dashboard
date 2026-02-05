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
    public String createUser(
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
}
