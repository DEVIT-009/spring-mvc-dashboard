package com.pos.dashboardmvc.controllers.admin;

import com.pos.dashboardmvc.models.Subject;
import com.pos.dashboardmvc.services.SubjectService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('SUBJECT_VIEW')")
    @GetMapping({"/", ""})
    public String index(Model model){
        model.addAttribute("pageTitle", "Subject Management");
        model.addAttribute("subjects", subjectService.listAll());

        return "admin/contents/subjects/index";
    }

    @PreAuthorize("hasAuthority('SUBJECT_VIEW')")
    @GetMapping("/create")
    public String formCreate(Model model){
        model.addAttribute("pageTitle", "Create Subject");
        model.addAttribute("subject", new Subject());

        return "admin/contents/subjects/create";
    }

    @PreAuthorize("hasAuthority('SUBJECT_CREATE')")
    @PostMapping("/create")
    public String createSubject(
            @Valid @ModelAttribute("subject") Subject subjectForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ){
        if(result.hasErrors()){
            return "admin/contents/subjects/create";
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
            return "admin/contents/subjects/create";
        }
        return "redirect:/admin/v1/subjects";
    }

    @PreAuthorize("hasAuthority('SUBJECT_VIEW')")
    @GetMapping("/update/{id}")
    public String formUpdate(@PathVariable int id, Model model) {
        Subject subject = subjectService.getSubjectById(id);

        model.addAttribute("subject", subject);
        model.addAttribute("pageTitle", "Edit Subject");

        return "admin/contents/subjects/update";
    }

    @PreAuthorize("hasAuthority('SUBJECT_UPDATE')")
    @PutMapping("/update/{id}")
    public String updateSubject(
            @PathVariable int id,
            @Valid @ModelAttribute("subject") Subject formSubject,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if(result.hasErrors()){
            return "admin/contents/subjects/create";
        }
        try {
            subjectService.update(id, formSubject);
            redirectAttributes.addFlashAttribute("success", "Subject updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update subject!");
        }

        return "redirect:/admin/v1/subjects/update/" + id;
    }

    @PreAuthorize("hasAuthority('SUBJECT_DELETE')")
    @DeleteMapping("/delete/{id}")
    public String deleteSubject(
            @PathVariable int id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            subjectService.delete(id);
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Subject deleted successfully!"
            );
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Subject not found!"
            );
        }
        return "redirect:/admin/v1/subjects";
    }
}
