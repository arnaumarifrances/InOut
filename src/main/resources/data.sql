-- Usuario ADMIN (contraseña: admin1234)
INSERT INTO user (dtype, name, email, password, role)
VALUES ('Admin', 'Admin User', 'admin@inout.com',
        '$2a$10$ZP2lLCEU9gWY9rWdOmvW0.zMFDP0VOJHICdx/lo9ZKMTZpcT8pL.e',
        'ADMIN');

-- Usuario EMPLOYEE (contraseña: employee1234)
INSERT INTO user (dtype, name, email, password, role, department, position)
VALUES ('Employee', 'Arni Employee', 'arni@inout.com',
        '$2a$10$kdxnD33NNayh9DbYj2N5OeO6gUdjkRuHG6u0g1t0jI3DqkEkJEnrC',
        'EMPLOYEE', 'IT', 'Developer');
