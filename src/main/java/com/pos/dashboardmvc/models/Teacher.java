package com.pos.dashboardmvc.models;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Teacher extends Person {

    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private Double salary;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{9,10}")
    private String phone;

}
