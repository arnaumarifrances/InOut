# InOut

InOut is a REST API developed with Java and Spring Boot, enabling employees to log their check-ins and check-outs, while administrators can view the shifts of all employees.

---

## 📊 Class Diagram

[UML Class Diagram.png](UML%20Class%20Diagram.png)
---

## 🚀 Setup

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
4. Run the application either from your IDE or by using the following Maven command:
   ```bash
   mvn spring-boot:run
   ```

---

## 🧱 Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security + JWT
- MySQL
- Lombok
- Postman / DBeaver (for testing)
- Maven

---

## 📦 Controllers & Routes

### 🧑‍💼 User Registration

- `POST /users/register`  
  → Registers a new employee.

### 🔐 Login

- `POST /api/login`  
  → Returns a JWT if the credentials are valid.

#### Example login with Postman:
- Method: POST
- URL: `http://localhost:8080/api/login`
- Body: raw (JSON)

```json
{
  "email": "arni@inout.com",
  "password": "employee1234"
}
```

→ Response: `{ "token": "..." }`

### ⏱️ Shifts

- `POST /shifts/checkin?employeeId={id}`  
  →  Logs the employee's check-in.
- `POST /shifts/checkout?employeeId={id}`  
  → Logs the employee's check-out.
- `GET /shifts/me?employeeId={id}`  
  → View the employee's own shifts.
- `GET /shifts` *(admin only)*  
  → View all shifts.
- `PATCH /shifts/{shiftId}`
  → Partially update a shift (e.g., update only the checkIn, checkOut, or totalHours). This method accepts a partial update of a shift.
  Example:

```json
  {
  "checkIn": "2024-07-01T09:00:00",
  "totalHours": 8.0
  }
```
- `DELETE /shifts/{shiftId}`
  → Deletes a specific shift by its shiftId. This method allows an admin to remove a shift from the system.

> ⚠️ All protected routes require a JWT in the header:
> `Authorization: Bearer <token>`

---

## 🔐 Roles & Security

- `ROLE_EMPLOYEE` → Can log their own check-ins/outs and view their shifts.
- `ROLE_ADMIN` → Can view all shifts and manage users.

---

## 🔄 Future Work

- Web dashboard for admins
- Notifications for tardiness
- Export shifts to Excel/PDF
- Mobile app

---

## 📁 Extra Links

- [Postman Collection](#)

---

## 👨‍💻 Team Members

- Arni (Full-stack Developer)

---

## 📚 Resources

- Ironhack Class Materials
- Spring Boot Documentation
- JWT & Spring Security Docs
- IA