package com.heriel.EmployeeManagement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * Data Transfer Object for Employee.
 */
@Data
public class EmployeeDTO {
    /**
     * The first name of the employee.
     * Must not be blank and must be between 2 and 50 characters.
     */
    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    /**
     * The last name of the employee.
     * Must not be blank and must be between 2 and 50 characters.
     */
    @NotBlank(message = "Last name is mandatory")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    /**
     * The email of the employee.
     * Must not be blank and must be a valid email address.
     */
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    /**
     * The department of the employee.
     * Must not be blank.
     */
    @NotBlank(message = "Department is mandatory")
    private String department;

    /**
     * The salary of the employee.
     * Must not be null, must be positive or zero, and must have a maximum of 10 digits and 2 decimals.
     */
    @NotNull(message = "Salary is mandatory")
    @PositiveOrZero(message = "Salary must be positive")
    @Digits(integer = 10, fraction = 2, message = "Salary must have maximum 10 digits and 2 decimals")
    private Double salary;
}