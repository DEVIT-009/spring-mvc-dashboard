package com.pos.dashboardmvc.controllers;
import com.pos.dashboardmvc.models.User;
import com.pos.dashboardmvc.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping({"", "/"})
    public String index(Model model) {
        model.addAttribute("pageTitle", "Manage Users");
        model.addAttribute("users", userService.listAll());

        return "contents/users/index";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "User Detail");

        return "contents/users/detail";
    }

    /*
        *** Create Method ***
    */

    @GetMapping("/create")
    public String formCreate(Model model) {
        model.addAttribute("pageTitle", "Create User");

        return "contents/users/create";
    }

    @PostMapping("/create")
    public String createUser(
        @ModelAttribute User formUser,
        @RequestParam("image") MultipartFile image,
        RedirectAttributes redirectAttributes
    ) {
        try {
            userService.create(formUser, image);
            redirectAttributes.addFlashAttribute("success", "User created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create user!");
            throw new RuntimeException(e);
        }

        return "redirect:/admin/v1/users";
    }

    /*
        *** Update Method ***
    */

    @GetMapping("/update/{id}")
    public String formUpdate(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);

        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "Edit User");

        return "contents/users/update";
    }

    @PostMapping("/update/{id}")
    public String updateUser(
            @PathVariable int id,
            @ModelAttribute User formUser,
            @RequestParam("image") MultipartFile image,
            RedirectAttributes redirectAttributes
    ) {
        try {
            userService.update(id, formUser, image);
            redirectAttributes.addFlashAttribute("success", "User updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update user!");
        }

        return "redirect:/admin/v1/users";
    }
}