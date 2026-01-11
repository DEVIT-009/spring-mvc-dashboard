package com.pos.dashboardmvc.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/v1/users")
public class UserController {

    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("pageTitle", "Create User");
        model.addAttribute("contentTemp" +
                "late", "contents/users/create :: create-user");
        return "index";
    }

    @GetMapping("/manage")
    public String manageUsers(Model model) {
        model.addAttribute("pageTitle", "Manage Users");
        model.addAttribute("contentTemplate", "contents/users/manage :: manage");
        return "index";
    }

    @GetMapping("/edit")
    public String editUser(Model model) {
        model.addAttribute("pageTitle", "Edit User");
        model.addAttribute("contentTemplate", "contents/users/edit :: edit");
        return "index";
    }

    @GetMapping("/detail")
    public String userDetail(Model model) {
        model.addAttribute("pageTitle", "User Detail");
        model.addAttribute("contentTemplate", "contents/users/detail :: detail");
        return "index";
    }
}