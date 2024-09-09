package com.heriel.EmployeeManagement.service;

import com.heriel.EmployeeManagement.dto.EmployeeDTO;
import com.heriel.EmployeeManagement.dto.EmployeeMapper;
import com.heriel.EmployeeManagement.model.Employee;
import com.heriel.EmployeeManagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service class for managing Employee entities.
 */
@Service
public class EmployeeService {
    @Autowired
    private final EmployeeRepository employeeRepository;

    /**
     * Constructor for EmployeeService.
     *
     * @param employeeRepository the repository for Employee entities
     */
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Adds a new employee.
     *
     * @param employeeDTO the data transfer object containing employee details
     * @return the saved Employee entity
     */
    public Employee addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = EmployeeMapper.toEntity(employeeDTO);
        return employeeRepository.save(employee);
    }

    /**
     * Retrieves all employees with pagination.
     *
     * @param pageable the pagination information
     * @return an iterable of Employee entities
     */
    public Iterable<Employee> getEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    /**
     * Retrieves an employee by ID.
     *
     * @param id the ID of the employee
     * @return the Employee entity, or null if not found
     */
    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    /**
     * Updates an existing employee.
     *
     * @param id          the ID of the employee to update
     * @param employeeDTO the data transfer object containing updated employee details
     * @return true if the employee was updated, false if the employee was not found
     */
    public boolean updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = EmployeeMapper.toEntity(employeeDTO);
        employee.setId(id);
        if (employeeRepository.existsById(id)) {
            employeeRepository.save(employee);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Deletes an employee by ID.
     *
     * @param id the ID of the employee to delete
     * @return true if the employee was deleted, false if the employee was not found
     */
    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}