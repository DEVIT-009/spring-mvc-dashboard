package com.pos.dashboardmvc.shared.exceptionHandling;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error-page")
public class ErrorPageController {

    @GetMapping("/401")
    public String unauthorize() {
        return "admin/errors/401";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "admin/errors/403";
    }

    @GetMapping("/404")
    public String notFound() {
        return "admin/errors/404";
    }

    @GetMapping("/500")
    public String serverError() {
        return "admin/errors/500";
    }
}