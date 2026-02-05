package com.pos.dashboardmvc.models;

import com.pos.dashboardmvc.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public abstract class Person {

    protected int id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50)
    protected String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50)
    protected String lastName;

    @NotNull
    protected Gender gender;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date must be in the past")
    protected LocalDate dob;

    @NotBlank(message = "Address is required")
    protected String address;

    @Size(max = 255)
    protected String imagePath;

    @NotBlank(message = "Subject name is required")
    protected String subjectName;
}
