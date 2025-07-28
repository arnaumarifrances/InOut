package com.inout.DataInitializer;

import com.inout.model.Employee;
import com.inout.enums.UserRole;
import com.inout.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) throws Exception {
        // Aquí puedes insertar los datos iniciales
        if (employeeRepository.count() == 0) {
            Employee admin = new Employee();
            admin.setName("Admin User");
            admin.setEmail("admin@inout.com");
            admin.setPassword("admin1234"); //ENCRIPTAR CONTRASEÑAS!!!
            admin.setRole(UserRole.ADMIN);
            admin.setDepartment("Administration");
            admin.setPosition("Administrator");

            employeeRepository.save(admin);

            Employee employee = new Employee();
            employee.setName("Employee User");
            employee.setEmail("employee@inout.com");
            employee.setPassword("employee1234"); //ENCRIPTAR CONTRASEÑAS!!!
            employee.setRole(UserRole.EMPLOYEE);
            employee.setDepartment("IT");
            employee.setPosition("Developer");

            employeeRepository.save(employee);
        }
    }
}
