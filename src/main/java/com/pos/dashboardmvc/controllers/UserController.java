package com.pos.dashboardmvc.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pos.dashboardmvc.models.dto.UserDTO;
import java.util.Date;

@Controller
@RequestMapping("/admin/v1/users")
public class UserController {

    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("pageTitle", "Create User");
        model.addAttribute("contentTemplate", "contents/users/create :: create-user");
        return "index";
    }

    @GetMapping("/manage")
    public String manageUsers(Model model) {
        model.addAttribute("pageTitle", "Manage Users");
        model.addAttribute("contentTemplate", "contents/users/manage :: manage-user");
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") long id, Model model) {
        // Create a mock user object for the template
        UserDTO user = createMockUser(id);
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "Edit User");
        model.addAttribute("contentTemplate", "contents/users/edit :: edit-user");
        return "index";
    }

    @GetMapping("/{id}")
    public String userDetail(@PathVariable long id, Model model) {
        // Create a mock user object for the template
        UserDTO user = createMockUser(id);
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "User Detail");
        model.addAttribute("contentTemplate", "contents/users/detail :: detail-user");
        return "index";
    }

    private UserDTO createMockUser(long id) {
        UserDTO user = new UserDTO();
        user.setId(id);
        user.setFullName("John Doe");
        user.setUsername("johndoe");
        user.setEmail("john@example.com");
        user.setPhone("+855 12 345 678");
        user.setRole("ADMIN");
        user.setStatus("ACTIVE");
        // Create dates 10 days ago and 1 day ago
        long now = System.currentTimeMillis();
        user.setCreatedAt(new Date(now));
        user.setUpdatedAt(new Date(now));
        return user;
    }
}