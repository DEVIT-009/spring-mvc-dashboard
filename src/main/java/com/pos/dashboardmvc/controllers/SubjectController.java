package com.pos.dashboardmvc.controllers;

import com.pos.dashboardmvc.models.Subject;
import com.pos.dashboardmvc.services.SubjectService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/v1/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService){
        this.subjectService = subjectService;
    }

    @GetMapping({"/", ""})
    public String index(Model model){
        model.addAttribute("pageTitle", "Subject Management");
        model.addAttribute("subjects", subjectService.listAll());

        return "contents/subjects/index";
    }

    @GetMapping("/create")
    public String formCreate(Model model){
        model.addAttribute("pageTitle", "Create Subject");
        model.addAttribute("subject", new Subject());

        return "contents/subjects/create";
    }

    @PostMapping("/create")
    public String createUser(
            @Valid @ModelAttribute("subject") Subject subjectForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ){
        if(result.hasErrors()){
            return "contents/subjects/create";
        }
        try {
            subjectService.create(subjectForm);
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Created subject successfully!"
            );

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Failed Create successfully!"
            );
            throw new RuntimeException(e);
        }
        return "redirect:/admin/v1/subjects";
    }
}
