package com.heriel.EmployeeManagement.controller;

import com.heriel.EmployeeManagement.dto.EmployeeDTO;
import com.heriel.EmployeeManagement.model.Employee;
import com.heriel.EmployeeManagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing employees.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    /**
     * Constructor for EmployeeController.
     *
     * @param employeeService the employee service
     */
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Adds a new employee.
     *
     * @param employeeDTO the employee data transfer object
     * @return ResponseEntity with a message and HTTP status
     */
    @PostMapping
    public ResponseEntity<String> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.addEmployee(employeeDTO);
        if (employee != null) {
            return new ResponseEntity("Employee added successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to add employee", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves all employees with pagination and sorting.
     *
     * @param page the page number (default is 0)
     * @param size the number of items per page (default is 10)
     * @param sort the sorting criteria (default is "id,asc")
     * @return ResponseEntity with a list of employees and HTTP status
     */
    @GetMapping
    public ResponseEntity<Iterable<Employee>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page, // Page number starts at 0
            @RequestParam(defaultValue = "10") int size, // Number of items per page
            @RequestParam(defaultValue = "id,asc") String[] sort // Sort by field, order
    ) {
        // Creating Sort object based on query parameters
        Sort.Direction sortDirection = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sorting = Sort.by(sortDirection, sort[0]);

        // Creating Pageable object
        Pageable pageable = PageRequest.of(page, size, sorting);

        // Fetch paginated employees
        Iterable<Employee> employees = employeeService.getEmployees(pageable);

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    /**
     * Retrieves an employee by ID.
     *
     * @param id the employee ID
     * @return ResponseEntity with the employee and HTTP status
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        Employee employee = employeeService.getEmployee(id);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates an existing employee.
     *
     * @param id          the employee ID
     * @param employeeDTO the employee data transfer object
     * @return ResponseEntity with a message and HTTP status
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        boolean isEmployeeUpdated = employeeService.updateEmployee(id, employeeDTO);
        if (isEmployeeUpdated) {
            return new ResponseEntity<>("Employee updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update employee", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes an employee by ID.
     *
     * @param id the employee ID
     * @return ResponseEntity with a message and HTTP status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        boolean isEmployeeDeleted = employeeService.deleteEmployee(id);
        if (isEmployeeDeleted) {
            return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete employee", HttpStatus.NOT_FOUND);
        }
    }
}