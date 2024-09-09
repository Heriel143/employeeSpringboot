package com.heriel.EmployeeManagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Entity class representing an Employee.
 */
@Data
@Entity
public class Employee {
    /**
     * The unique identifier for the employee.
     * Generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The first name of the employee.
     */
    private String firstName;

    /**
     * The last name of the employee.
     */
    private String lastName;

    /**
     * The email address of the employee.
     */
    private String email;

    /**
     * The department where the employee works.
     */
    private String department;

    /**
     * The salary of the employee.
     */
    private Double salary;
}