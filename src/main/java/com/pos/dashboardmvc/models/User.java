package com.pos.dashboardmvc.models;

import com.pos.dashboardmvc.shared.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class User extends BaseModel {

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    @Size(max = 255, message = "Image path must not exceed 255 characters")
    private String imagePath;

    private LocalDateTime createdAt;  // better than Date for Spring Boot
    private LocalDateTime updatedAt;

    public User(
        int id, String fullName, String username,
        String email, String phone, Set<Role> role,
        UserStatus status, String imagePath, String password
    ) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.roles = role;
        this.status = status;
        this.imagePath = imagePath;
        this.password = password;
    }

}
