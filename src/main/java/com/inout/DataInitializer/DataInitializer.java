package com.inout.DataInitializer;

import com.inout.model.Employee;
import com.inout.model.Shift;
import com.inout.enums.UserRole;
import com.inout.repository.EmployeeRepository;
import com.inout.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Verify data in DB
        if (employeeRepository.count() == 0) {

            // Create admin user
            Employee admin = new Employee();
            admin.setName("Admin User");
            admin.setEmail("admin@inout.com");
            admin.setPassword(passwordEncoder.encode("admin1234")); // Encriptar contraseña
            admin.setRole(UserRole.ADMIN);
            admin.setDepartment("Administration");
            admin.setPosition("Administrator");

            employeeRepository.save(admin);
            System.out.println("Admin user created");

            // Create employee user
            Employee employee = new Employee();
            employee.setName("Employee User");
            employee.setEmail("employee@inout.com");
            employee.setPassword(passwordEncoder.encode("employee1234")); // Encriptar contraseña
            employee.setRole(UserRole.EMPLOYEE);
            employee.setDepartment("Computer engineering");
            employee.setPosition("Backend Developer");

            employeeRepository.save(employee);
            System.out.println("Employee user created");


            // Create shift for admin
            Shift adminShift = new Shift();
            adminShift.setEmployee(admin);
            adminShift.setCheckIn(LocalDateTime.now().minusHours(8));
            adminShift.setCheckOut(LocalDateTime.now().minusHours(4));
            adminShift.setTotalHours(4.0);

            shiftRepository.save(adminShift);
            System.out.println("Admin shift created");

            // Create shift for employee
            Shift employeeShift = new Shift();
            employeeShift.setEmployee(employee);
            employeeShift.setCheckIn(LocalDateTime.now().minusHours(6));
            employeeShift.setCheckOut(LocalDateTime.now().minusHours(2));
            employeeShift.setTotalHours(4.0);

            shiftRepository.save(employeeShift);
            System.out.println("Employee shift created");
        }
    }
}
