package com.pos.dashboardmvc.models;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Student extends Person {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;
}