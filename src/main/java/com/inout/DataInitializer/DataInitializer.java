package com.inout.DataInitializer;

import com.inout.model.Employee;
import com.inout.enums.UserRole;
import com.inout.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {

        if (employeeRepository.count() == 0) {
            // Admin initial data
            Employee admin = new Employee();
            admin.setName("Admin User");
            admin.setEmail("admin@inout.com");
            admin.setPassword(passwordEncoder.encode("admin1234")); //encrypt password
            admin.setRole(UserRole.ADMIN);
            admin.setDepartment("Administration");
            admin.setPosition("Administrator");

            employeeRepository.save(admin);

            // Employee initial data
            Employee employee = new Employee();
            employee.setName("Employee User");
            employee.setEmail("employee@inout.com");
            employee.setPassword(passwordEncoder.encode("employee1234")); //encrypt password
            employee.setRole(UserRole.EMPLOYEE);
            employee.setDepartment("Computer engineering");
            employee.setPosition("Backend Developer");

            employeeRepository.save(employee);
        }
    }
}
