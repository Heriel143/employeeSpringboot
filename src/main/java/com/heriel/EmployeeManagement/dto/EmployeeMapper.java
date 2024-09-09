package com.heriel.EmployeeManagement.dto;

import com.heriel.EmployeeManagement.model.Employee;

/**
 * Mapper class for converting between EmployeeDTO and Employee entities.
 */
public class EmployeeMapper {

    /**
     * Converts an EmployeeDTO to an Employee entity.
     *
     * @param dto the EmployeeDTO to convert
     * @return the converted Employee entity
     */
    public static Employee toEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        employee.setSalary(dto.getSalary());
        return employee;
    }
}