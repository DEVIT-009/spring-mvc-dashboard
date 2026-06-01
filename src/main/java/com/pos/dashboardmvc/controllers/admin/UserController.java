package com.pos.dashboardmvc.controllers.admin;
import com.pos.dashboardmvc.models.User;
import com.pos.dashboardmvc.repositories.RoleRepository;
import com.pos.dashboardmvc.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@PreAuthorize("hasAuthority('USER_')")
@RequestMapping("/admin/v1/users")
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public UserController(
            UserService userService,
            RoleRepository roleRepository
    ){
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PreAuthorize("hasAuthority('USER_VIEW')")
    @GetMapping({"", "/"})
    public String index(Model model) {
        model.addAttribute("pageTitle", "Manage Users");
        model.addAttribute("users", userService.listAll());

        return "admin/contents/users/index";
    }

    @PreAuthorize("hasAuthority('USER_VIEW')")
    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "User Detail");

        return "admin/contents/users/detail";
    }

    /*
        *** Create Method ***
    */
    @PreAuthorize("hasAuthority('USER_VIEW')")
    @GetMapping("/create")
    public String formCreate(Model model) {
        model.addAttribute("pageTitle", "Create User");

        return "admin/contents/users/create";
    }

    @PreAuthorize("hasAuthority('USER_CREATE')")
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

    @PreAuthorize("hasAuthority('USER_VIEW')")
    @GetMapping("/update/{id}")
    public String formUpdate(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);

        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("pageTitle", "Edit User");

        return "admin/contents/users/update";
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PutMapping("/update/{id}")
    public String updateUser(
            @PathVariable int id,
            @ModelAttribute User formUser,
            @RequestParam("roleId") Integer roleId,
            @RequestParam("image") MultipartFile image,
            RedirectAttributes redirectAttributes
    ) {
        try {
            userService.update(
                    id,
                    formUser,
                    roleId,
                    image
            );

            redirectAttributes.addFlashAttribute(
                    "success",
                    "User updated successfully!"
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Failed to update user!"
            );
        }

        return "redirect:/admin/v1/users/update/" + id;
    }

    @PreAuthorize("hasAuthority('USER_DELETE')")
    @DeleteMapping("/delete/{id}")
    public String deleteUser(
            @PathVariable int id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            userService.delete(id);
            redirectAttributes.addFlashAttribute(
                    "success",
                    "User deleted successfully!"
            );
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "User not found!"
            );
        }
        return "redirect:/admin/v1/users";
    }
}