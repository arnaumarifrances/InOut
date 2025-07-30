# InOut - Attendance Management System 🕒

---

## Overview 🚀

**InOut** is an employee attendance management system designed to simplify the process of tracking employee work hours. The application allows employees to check in and check out, and enables administrators to manage and view employee shifts and records.

The system includes features such as:
- 🧑‍💼 **Employee login and registration**.
- ⏰ **Check-in and check-out functionality**.
- 📊 **Shift tracking and management**.
- 🏢 **Admin view of all shifts**.

---

## 📊 Class Diagram
[UML Class Diagram.png](UML%20Class%20Diagram.png)

---

## 🧱Technologies Used ⚙️
- **Spring Boot**: The core framework for building the backend of the application.
- **Spring Security**: For handling authentication and authorization with **JWT tokens**.
- **Spring Data JPA**: For database access and managing entities.
- **MySQL**: The database used for storing employee and shift data.
- **JWT (JSON Web Tokens)**: For secure authentication and authorization.
- **Lombok**: To reduce boilerplate code with annotations for getters, setters, constructors, etc.
- **JUnit 5**: For unit testing the application.
- **Maven**: For dependency management and building the project.
- **IntelliJ IDEA**: The IDE used for development.

---

## ⚙️ Setup

1. Clone the repository.
2. Create a MySQL database called `inout_db`.
3. Configure `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3314/inout_db?createDatabaseIfNotExist=true&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=Ironhack
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```
4. Run the application from your IDE or use the following command:
   ```bash
   mvn spring-boot:run
   ```
   
---

## 📦 Controllers and Routes 📬

### 🧑‍💼 **Register**

- `POST /users/register`  
  → Register a new employee.

### 🔐 **Login**

- `POST /api/login`  
  → Returns a **JWT** if the credentials are valid.

#### Example of login with Postman
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/login`
- **Body**: raw (JSON)

 ```json
{
  "email": "arni@inout.com",
  "password": "employee1234"
}
```

→ Expected Response: `{ "token": "..." }`

### ⏱️ Shifts
- `POST /shifts/checkin?employeeId={id}`  
  → Check in
- `POST /shifts/checkout?employeeId={id}`  
  → Check out
- `GET /shifts/me?employeeId={id}`  
  → View own shifts
- `GET /shifts` *(solo admin)*  
  → View all shifts

> ⚠️ All protected routes require JWT in the header:  
> `Authorization: Bearer <token>`

---

## 🔐 Roles and Security

- `ROLE_EMPLOYEE` →  Can clock in and view their own shifts.
- `ROLE_ADMIN` → Can view all shifts and manage users.

---

## ⚡Key Development Challenges 

1. **JWT Authentication**:
  - Implementing secure authentication using **JWT tokens** was a key challenge, as it required careful handling of tokens for both employee and admin roles, ensuring that only authorized users could access certain resources.

2. **Shift Management**:
  - Developing a system that accurately tracks employee shifts, handles **check-in/check-out** logic, and calculates the total hours worked involved careful planning of the business logic.

3. **Database Design**:
  - Ensuring the right relationships between entities like `Employee`, `Shift`, and `User` was critical to allow flexible querying and efficient data retrieval.

4. **Spring Security Configuration**:
  - Configuring **Spring Security** to handle both **admin** and **employee** roles, with specific access permissions for each, was a complex but necessary part of the system.

---

## Future Implementations 💡

1. **Advanced Reporting**:
  - Adding features to generate reports for admins, such as **total hours worked**, **employee attendance**, and other statistics.

2. **Mobile App Integration**:
  - Potential integration with **mobile apps** to allow employees to clock in and out from their phones.

3. **Notifications**:
  - Implementing **notifications** for employees and admins about upcoming shifts, missed check-ins, or errors in the shift data.

4. **Shift Approval Workflow**:
  - Adding an **approval workflow** for employees' shifts, where managers or HR can approve or modify shift records.

5. **Employee Dashboard**:
  - A more detailed **dashboard** for employees to see their shift history, upcoming shifts, and performance metrics.

---

## 👨‍💻 Team Members

- Arni (Full stack Developer)

---

## 📚 Resources

- Ironhack Class Materials
- Spring Boot Documentation
- JWT & Spring Security Docs