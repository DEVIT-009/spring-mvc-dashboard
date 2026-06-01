package com.pos.dashboardmvc.controllers.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/v1")
public class DashboardController {

    @PreAuthorize("hasAuthority('DASHBOARD_VIEW')")
    @GetMapping({"", "/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "Admin Dashboard");
        return "admin/contents/analyst";
    }

    @PreAuthorize("hasAuthority('ANALYST_VIEW')")
    @GetMapping("/analyst")
    public String analyst(Model model) {
        model.addAttribute("pageTitle", "Analyst");
        return "admin/contents/analyst";
    }
}