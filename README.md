# InOut

**InOut** es una API REST desarrollada con Java y Spring Boot que permite a empleados fichar su entrada y salida del trabajo, y a los administradores consultar los turnos de todos los empleados.

---

## 📊 Class Diagram

[UML Class Diagram.png](UML%20Class%20Diagram.png)
---

## 🚀 Setup

1. Clona el repositorio.
2. Crea una base de datos MySQL llamada `inout_db`
3. Configura `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3314/inout_db?createDatabaseIfNotExist=true&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=Ironhack
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```
4. Ejecuta la aplicación desde tu IDE o con:
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
- Postman / DBeaver (para pruebas)
- Maven

---

## 📦 Controllers & Routes

### 🧑‍💼 Registro

- `POST /users/register`  
  → Registra un nuevo empleado

### 🔐 Login

- `POST /api/login`  
  → Devuelve un JWT si las credenciales son válidas

#### Ejemplo de login con Postman
- Método: POST
- URL: `http://localhost:8080/api/login`
- Body: raw (JSON)

```json
{
  "email": "arni@inout.com",
  "password": "employee1234"
}
```

→ Respuesta: `{ "token": "..." }`

### ⏱️ Fichajes

- `POST /shifts/checkin?employeeId={id}`  
  → Fichar entrada
- `POST /shifts/checkout?employeeId={id}`  
  → Fichar salida
- `GET /shifts/me?employeeId={id}`  
  → Ver turnos propios
- `GET /shifts` *(solo admin)*  
  → Ver todos los turnos

> ⚠️ Todas las rutas protegidas requieren JWT en el header:  
> `Authorization: Bearer <token>`

---

## 🔐 Roles y Seguridad

- `ROLE_EMPLOYEE` → Puede fichar y ver sus propios turnos.
- `ROLE_ADMIN` → Puede ver todos los fichajes y gestionar usuarios.

---

## 🔄 Future Work

- Dashboard web para admins
- Notificaciones de retrasos
- Exportar fichajes a Excel/PDF
- App móvil

---

## 📁 Extra Links

- [Trello](#)
- [Presentación](#)
- [Postman Collection](#)

---

## 👨‍💻 Team Members

- Arni (Desarrollador fullstack)

---

## 📚 Resources

- Ironhack Class Materials
- Spring Boot Documentation
- JWT & Spring Security Docs