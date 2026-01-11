package com.pos.dashboardmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/v1")
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "Admin Dashboard");
        model.addAttribute("appVersion", "2.3.0");

        // This tells main.html which fragment to inject
        model.addAttribute(
            "contentTemplate",
            "contents/dashboard :: dashboard"
        );

        return "index"; // <- always return main layout
    }

    @GetMapping("/report")
    public String report(Model model) {
        model.addAttribute("pageTitle", "Report");
        model.addAttribute("appVersion", "2.3.0");

        // This tells main.html which fragment to inject
        model.addAttribute(
            "contentTemplate",
            "contents/report :: report"
        );

        return "index"; // <- always return main layout
    }

    @GetMapping("/analyst")
    public String analyst(Model model) {
        model.addAttribute("pageTitle", "Analyst");
        model.addAttribute("appVersion", "2.3.0");

        // This tells main.html which fragment to inject
        model.addAttribute(
            "contentTemplate",
            "contents/analyst :: analyst"
        );

        return "index"; // <- always return main layout
    }
}