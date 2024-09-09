package com.heriel.EmployeeManagement.service;

import com.heriel.EmployeeManagement.dto.EmployeeDTO;
import com.heriel.EmployeeManagement.dto.EmployeeMapper;
import com.heriel.EmployeeManagement.model.Employee;
import com.heriel.EmployeeManagement.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the EmployeeService class.
 */
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    /**
     * Sets up the test environment by initializing mocks.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the addition of a new employee.
     */
    @Test
    void testAddEmployee() {
        // Arrange
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setEmail("johndoe@gmail.com");
        employeeDTO.setDepartment("Marketing");
        employeeDTO.setSalary(50000.0);

        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("johndoe@gmail.com");
        employee.setDepartment("Marketing");
        employee.setSalary(50000.0);

        // Mock the static method EmployeeMapper.toEntity()
        try (MockedStatic<EmployeeMapper> mockedMapper = mockStatic(EmployeeMapper.class)) {
            mockedMapper.when(() -> EmployeeMapper.toEntity(employeeDTO)).thenReturn(employee);

            // Mock the repository
            when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

            // Act
            Employee result = employeeService.addEmployee(employeeDTO);

            // Assert
            assertNotNull(result);
            assertEquals("John", result.getFirstName());
            assertEquals("Doe", result.getLastName());
            assertEquals("johndoe@gmail.com", result.getEmail());
            assertEquals("Marketing", result.getDepartment());
            assertEquals(50000.0, result.getSalary());
            verify(employeeRepository, times(1)).save(employee);
        }
    }

    /**
     * Tests the retrieval of all employees with pagination.
     */
    @Test
    void testGetEmployees() {
        // Arrange
        Employee employee1 = new Employee();
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setEmail("johndoe@gmail.com");
        employee1.setDepartment("Marketing");
        employee1.setSalary(50000.0);

        Employee employee2 = new Employee();
        employee2.setFirstName("Jane");
        employee2.setLastName("Smith");
        employee2.setEmail("janesmith@gmail.com");
        employee2.setDepartment("Sales");
        employee2.setSalary(55000.0);

        List<Employee> employees = Arrays.asList(employee1, employee2);
        Page<Employee> employeePage = new PageImpl<>(employees);

        Pageable pageable = PageRequest.of(0, 10);
        when(employeeRepository.findAll(pageable)).thenReturn(employeePage);

        // Act
        Iterable<Employee> result = employeeService.getEmployees(pageable);

        // Assert
        assertNotNull(result);
        // Convert the Iterable to a List for assertion
        List<Employee> resultList = ((Page<Employee>) result).getContent();
        assertEquals(2, resultList.size());
        verify(employeeRepository, times(1)).findAll(pageable);
    }

    /**
     * Tests the retrieval of an employee by ID.
     */
    @Test
    void testGetEmployee() {
        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("johndoe@gmail.com");
        employee.setDepartment("Marketing");
        employee.setSalary(50000.0);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // Act
        Employee result = employeeService.getEmployee(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("johndoe@gmail.com", result.getEmail());
        assertEquals("Marketing", result.getDepartment());
        assertEquals(50000.0, result.getSalary());
        verify(employeeRepository, times(1)).findById(1L);
    }

    /**
     * Tests the successful update of an existing employee.
     */
    @Test
    void testUpdateEmployeeSuccess() {
        // Arrange
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setEmail("johndoe@gmail.com");
        employeeDTO.setDepartment("Marketing");
        employeeDTO.setSalary(50000.0);

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John2");
        employee.setLastName("Doe2");
        employee.setEmail("johndoe2@gmail.com");
        employee.setDepartment("Marketing2");
        employee.setSalary(50000.0);

        when(employeeRepository.existsById(1L)).thenReturn(true);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // Mock the static method
        try (MockedStatic<EmployeeMapper> mockedMapper = mockStatic(EmployeeMapper.class)) {
            mockedMapper.when(() -> EmployeeMapper.toEntity(employeeDTO)).thenReturn(employee);

            // Act
            boolean result = employeeService.updateEmployee(1L, employeeDTO);

            // Assert
            assertTrue(result);
            verify(employeeRepository, times(1)).save(employee);
        }
    }

    /**
     * Tests the failure to update a non-existing employee.
     */
    @Test
    void testUpdateEmployeeFailure() {
        // Arrange
        EmployeeDTO employeeDTO = new EmployeeDTO();

        when(employeeRepository.existsById(1L)).thenReturn(false);

        // Act
        boolean result = employeeService.updateEmployee(1L, employeeDTO);

        // Assert
        assertFalse(result);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    /**
     * Tests the successful deletion of an employee.
     */
    @Test
    void testDeleteEmployeeSuccess() {
        // Arrange
        when(employeeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1L);

        // Act
        boolean result = employeeService.deleteEmployee(1L);

        // Assert
        assertTrue(result);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    /**
     * Tests the failure to delete a non-existing employee.
     */
    @Test
    void testDeleteEmployeeFailure() {
        // Arrange
        when(employeeRepository.existsById(1L)).thenReturn(false);

        // Act
        boolean result = employeeService.deleteEmployee(1L);

        // Assert
        assertFalse(result);
        verify(employeeRepository, never()).deleteById(1L);
    }
}