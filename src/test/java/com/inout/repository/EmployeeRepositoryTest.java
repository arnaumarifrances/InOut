package com.inout.repository;

import com.inout.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class EmployeeRepositoryTest {

    @Mock
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Set up an employee instance for testing
        employee = new Employee();
        employee.setId(1L);
        employee.setEmail("john.doe@example.com");
    }

    @Test
    void testFindByEmail() {
        // Simulate the repository behavior
        when(employeeRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(employee));

        // Call the repository method
        Optional<Employee> foundEmployee = employeeRepository.findByEmail("john.doe@example.com");

        // Verify the response
        assertThat(foundEmployee).isPresent();
        assertThat(foundEmployee.get().getEmail()).isEqualTo("john.doe@example.com");
    }
}
