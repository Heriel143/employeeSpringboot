package com.heriel.EmployeeManagement.repository;

import com.heriel.EmployeeManagement.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Employee entities.
 * Extends JpaRepository to provide CRUD operations and pagination.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    /**
     * Fetches all employees with pagination.
     *
     * @param pageable the pagination information
     * @return a page of employees
     */
    Page<Employee> findAll(Pageable pageable);
}