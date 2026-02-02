package com.pos.dashboardmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/v1")
public class DashboardController {

    @GetMapping({"", "/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "Admin Dashboard");
        return "contents/index";
    }

    @GetMapping("/analyst")
    public String analyst(Model model) {
        model.addAttribute("pageTitle", "Analyst");
        return "contents/analyst";
    }
}