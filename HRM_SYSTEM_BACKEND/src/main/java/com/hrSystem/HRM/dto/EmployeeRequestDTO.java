package com.hrSystem.HRM.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDTO {

    private String employeeId;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Department is required")
    @Size(max = 50, message = "Department must not exceed 50 characters")
    private String department;

    @NotBlank(message = "Role is required")
    @Size(max = 100, message = "Role must not exceed 100 characters")
    private String role;

    @Size(max = 1000, message = "Skills must not exceed 1000 characters")
    private String skills;

    @Min(value = 1, message = "Skill level must be at least 1")
    @Max(value = 10, message = "Skill level must be at most 10")
    private Integer skillLevel = 1;

    @Min(value = 0, message = "Experience cannot be negative")
    private Double experience = 0.0;

    @Size(max = 50, message = "Category must not exceed 50 characters")
    private String category = "Full-time";

    @Size(max = 50, message = "Availability must not exceed 50 characters")
    private String availability = "Available";

    @Min(value = 0, message = "Performance rating cannot be negative")
    @Max(value = 10, message = "Performance rating must be at most 10")
    private Double performanceRating = 0.0;

    @Size(max = 50, message = "Status must not exceed 50 characters")
    private String status = "Present";
}

