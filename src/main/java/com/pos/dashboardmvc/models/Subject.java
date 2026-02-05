package com.pos.dashboardmvc.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Subject {

    private int id;

    @NotBlank(message = "Subject name is required")
    @Size(min=3, max = 50, message = "Subject name must not exceed 100 characters")
    private String subjectName;

    // Optional: all-args constructor if needed
    public Subject(int id, String subjectName) {
        this.id = id;
        this.subjectName = subjectName;
    }
}
