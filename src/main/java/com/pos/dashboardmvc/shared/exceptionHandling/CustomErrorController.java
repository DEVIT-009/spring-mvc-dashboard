package com.pos.dashboardmvc.shared.exceptionHandling;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {

        Object status = request.getAttribute(
                RequestDispatcher.ERROR_STATUS_CODE
        );

        if (status != null) {

            int statusCode = Integer.parseInt(status.toString());

            switch (statusCode) {

                case 401:
                    return "redirect:/error-page/401";
                case 403:
                    return "redirect:/error-page/403";
                case 404:
                    return "redirect:/error-page/404";
                case 500:
                    return "redirect:/error-page/500";
            }
        }

        return "redirect:/error-page/500";
    }
}