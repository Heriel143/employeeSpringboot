package com.heriel.EmployeeManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heriel.EmployeeManagement.dto.EmployeeDTO;
import com.heriel.EmployeeManagement.model.Employee;
import com.heriel.EmployeeManagement.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the EmployeeController.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Sets up the test environment by clearing the database before each test.
     */
    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll(); // Clear the database before each test
    }

    /**
     * Tests the addition of a new employee.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testAddEmployee() throws Exception {
        // Arrange
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setEmail("johndoe@gmail.com");
        employeeDTO.setDepartment("Marketing");
        employeeDTO.setSalary(50000.0);

        // Act & Assert
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Employee added successfully"));
    }

    /**
     * Tests the retrieval of all employees.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testGetEmployees() throws Exception {
        // Arrange
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("johndoe@gmail.com");
        employee.setDepartment("Marketing");
        employee.setSalary(50000.0);
        employeeRepository.save(employee);

        // Act & Assert
        mockMvc.perform(get("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("John"))
                .andExpect(jsonPath("$.content[0].lastName").value("Doe"))
                .andExpect(jsonPath("$.content[0].email").value("johndoe@gmail.com"))
                .andExpect(jsonPath("$.content[0].department").value("Marketing"))
                .andExpect(jsonPath("$.content[0].salary").value(50000.0));
    }

    /**
     * Tests the update of an existing employee.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testUpdateEmployee() throws Exception {
        // Arrange
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("johndoe@gmail.com");
        employee.setDepartment("Marketing");
        employee.setSalary(50000.0);
        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO();
        updatedEmployeeDTO.setFirstName("Jane");
        updatedEmployeeDTO.setLastName("Smith");
        updatedEmployeeDTO.setEmail("janesmith@gmail.com");
        updatedEmployeeDTO.setDepartment("Sales");
        updatedEmployeeDTO.setSalary(60000.0);

        // Act & Assert
        mockMvc.perform(put("/api/employees/{id}", savedEmployee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmployeeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee updated successfully"));
    }

    /**
     * Tests the deletion of an employee.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testDeleteEmployee() throws Exception {
        // Arrange
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("johndoe@gmail.com");
        employee.setDepartment("Marketing");
        employee.setSalary(50000.0);
        Employee savedEmployee = employeeRepository.save(employee);

        // Act & Assert
        mockMvc.perform(delete("/api/employees/{id}", savedEmployee.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee deleted successfully"));
    }
}