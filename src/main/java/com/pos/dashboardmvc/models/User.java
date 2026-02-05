package com.pos.dashboardmvc.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class User {
    private int id;

    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 100, message = "Full name must be between 3 and 100 characters")
    private String fullName;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{9,15}", message = "Phone must be 9-15 digits")
    private String phone;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "ADMIN|USER|MANAGER", message = "Role must be ADMIN, USER, or MANAGER")
    private String role;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "Status must be ACTIVE or INACTIVE")
    private String status;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    @Size(max = 255, message = "Image path must not exceed 255 characters")
    private String imagePath;

    private LocalDateTime createdAt;  // better than Date for Spring Boot
    private LocalDateTime updatedAt;

    public User(
        int id, String fullName, String username,
        String email, String phone, String role,
        String status, String imagePath, String password
    ) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.imagePath = imagePath;
        this.password = password;
    }

}
