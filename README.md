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
![UML Class Diagram.png](UML%20Class%20Diagram.png)

---

## 🧱Technologies Used ⚙️
- **IntelliJ IDEA**: The IDE used for development.
- **Spring Boot**: The core framework for building the backend of the application.
- **Spring Data JPA**: For database access and managing entities.
-  **MySQL**: The database used for storing employee and shift data.
- **Lombok**: To reduce boilerplate code with annotations for getters, setters, constructors, etc.
- **JUnit 5**: For unit testing the application.
- **Spring Security**: For handling authentication and authorization with **JWT tokens**.
- **JWT (JSON Web Tokens)**: For secure authentication and authorization.
- **Lombok**: To reduce boilerplate code with annotations for getters, setters, constructors, etc.
- **Maven**: For dependency management and building the project.

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

1. **Shift Management**:
  - Developing a system that accurately tracks employee shifts, handles **check-in/check-out** logic, and calculates the total hours worked involved careful planning of the business logic.

3. **Database Design**:
  - Ensuring the right relationships between entities like `Employee`, `Shift`, and `User` was critical to allow flexible querying and efficient data retrieval.

4. **Spring Security Configuration**:
  - Configuring **Spring Security** to handle both **admin** and **employee** roles, with specific access permissions for each, was a complex but necessary part of the system.

5. **JWT Authentication**:
  - Implementing secure authentication using **JWT tokens** was a key challenge, as it required careful handling of tokens for both employee and admin roles, ensuring that only authorized users could access certain resources.
---

## Future Implementations 💡

1. **Scalable and Robust System**:
  - Enhance the system's architecture to ensure scalability and robustness as the number of users and data grows.

2. **Implement Graphs and Calendars**:
  - Add visual features such as attendance graphs and interactive calendars so employees and administrators can easily view shifts and statistics.

3. **Frontend**:
  - Develop a more intuitive and user-friendly interface, improving the experience for both employees and administrators..

4. **Mobile App**:
  - Create a mobile app that allows employees to clock in and out directly from their phones, improving flexibility and accessibility.

5. **RFID Card-Based Time Tracking System**:
  - Integrate an automated time tracking system using RFID cards to simplify the check-in/check-out process and avoid human errors.

6. **Client Meetings for Specifications (URS)**:
- Engage with the client to gain a clearer understanding of user specifications and needs, adapting the system to those requirements.
---

## 👨‍💻 Team Members

- Arni (Full stack Developer)

---

## 📚 Resources

- Ironhack Class Materials
- Spring Boot Documentation
- JWT & Spring Security Docs