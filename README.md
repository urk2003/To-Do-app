# 📝 TodoApp – Spring Boot + Thymeleaf

A modern to-do list web application built using **Spring Boot**, **Thymeleaf**, and **MySQL**, featuring user authentication, task planning, calendar views, and responsive UI.

**👤 Created by: UTKARSH KHANDELWAL**

---

## 🚀 Features

- ✅ User Registration & Login (with password encryption)
- ✅ Create, view, complete, and delete tasks
- ✅ Filter tasks by date
- ✅ Monthly calendar view (`/calendar`)
- ✅ Task planner overview (`/planner`)
- ✅ Responsive frontend using Bootstrap
- ✅ CSRF Protection
- ✅ Hibernate JPA + MySQL integration
- ✅ Secure with Spring Security

---

## 🧱 Tech Stack

- Java 17+
- Spring Boot 3.5.x
- Spring Data JPA
- Spring Security
- Thymeleaf
- MySQL
- Bootstrap 5

---

## 🛠️ Setup Instructions

### 1. 📦 Clone the repository

```bash
git clone https://github.com/yourusername/todoapp.git
cd todoapp
```

### 2. ⚙️ Configure `application.properties`

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/todo-app
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true
```

> ⚠️ Avoid committing DB credentials. Use environment variables or secrets management in production.

---

### 3. 🧪 Run the app

```bash
./mvnw spring-boot:run
```

Then open: [http://localhost:8080](http://localhost:8080)

---

## 🖼️ UI Pages

- `/login` – User login
- `/register` – User registration
- `/` – Daily task view (with create/delete/toggle)
- `/calendar` – Monthly calendar of tasks
- `/planner` – All days with tasks listed

---

## ✅ CSRF Protection

All forms include:

```html
<input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
```

---

## 🧹 Known Fixes Applied

- ✅ Removed use of `#request` in Thymeleaf (not allowed in Spring Boot 3+)
- ✅ Added `activeTab` model attributes to highlight nav tabs
- ✅ Fixed potential null task titles
- ✅ Applied CSRF protection to all forms
- ✅ Improved security for password handling

---

## 📁 Directory Structure (Simplified)

```
src/
├── main/
│   ├── java/com/todo/todoapp/
│   │   ├── controllers/
│   │   ├── models/
│   │   ├── repos/
│   │   ├── services/
│   │   └── config/
│   └── resources/
│       ├── templates/
│       │   ├── login.html
│       │   ├── register.html
│       │   ├── tasks.html
│       │   ├── planner.html
│       │   └── calendar.html
│       └── application.properties
```

---

## 🤝 Contributing

1. Fork the repo
2. Create your feature branch: `git checkout -b feature/your-feature`
3. Commit your changes
4. Push and create a Pull Request

---

## 📜 License

This project is licensed under the MIT License. See `LICENSE` for more info.
