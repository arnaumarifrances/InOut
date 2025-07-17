package com.inout.service;

import com.inout.dto.RegisterEmployeeDTO;
import com.inout.dto.UserDTO;
import com.inout.model.Employee;
import com.inout.model.UserRole;
import com.inout.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerEmployee(RegisterEmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPassword(passwordEncoder.encode(dto.getPassword())); // encripta la contraseña
        employee.setRole(UserRole.EMPLOYEE);
        employee.setDepartment(dto.getDepartment());
        employee.setPosition(dto.getPosition());

        Employee saved = employeeRepository.save(employee);

        return new UserDTO(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getRole().name()
        );
    }
}
