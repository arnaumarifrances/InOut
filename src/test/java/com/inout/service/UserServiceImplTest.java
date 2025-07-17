package com.inout.service;

import com.inout.dto.RegisterEmployeeDTO;
import com.inout.dto.UserDTO;
import com.inout.model.Employee;
import com.inout.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void testRegisterEmployee_success() {
        RegisterEmployeeDTO dto = new RegisterEmployeeDTO(
                "Alice", "alice@inout.com", "pass1234", "Sales", "Manager"
        );

        when(passwordEncoder.encode("pass1234")).thenReturn("ENCRYPTED_PASS");
        when(employeeRepository.save(any())).thenAnswer(inv -> {
            Employee emp = inv.getArgument(0);
            emp.setId(100L); // Simular ID asignado por la BD
            return emp;
        });

        UserDTO result = userService.registerEmployee(dto);

        assertEquals("Alice", result.getName());
        assertEquals("alice@inout.com", result.getEmail());
        assertEquals("EMPLOYEE", result.getRole());
    }
}
