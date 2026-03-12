package com.pos.dashboardmvc.models;

import com.pos.dashboardmvc.enums.Gender;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Teacher extends BaseModel {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50)
    private String lastName;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date must be in the past")
    private LocalDate dob;

    @NotBlank(message = "Address is required")
    private String address;

    @Size(max = 255)
    private String imagePath;

    @NotBlank(message = "Subject name is required")
    private String subjectName;

    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private Double salary;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{9,10}")
    private String phone;
}
