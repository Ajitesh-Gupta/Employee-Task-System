# Employee Task System

A simple system to manage employees, departments, and tasks. Built with Java, Quarkus, and PostgreSQL.

## 📋 Project Overview
This application allows you to:
- Manage employees and departments
- Assign and track tasks
- View dashboards and statistics
- Access RESTful APIs with Swagger UI

## 🛠 Prerequisites
- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- Git

## 🚀 Setup Instructions

### 1. Clone and Build
```bash
git clone <your-repo-url>
cd employee-task-system
mvn clean install
```

### 2. Start PostgreSQL
```bash
docker-compose up -d
```

### 3. Run Quarkus
```bash
mvn quarkus:dev
```

### 4. Test
Visit: http://localhost:8080/hello

### 5. API Docs
- Swagger UI: http://localhost:8080/q/swagger-ui
- OpenAPI spec: http://localhost:8080/openapi

## 📚 Example API Endpoints
- `GET /employees` — List all employees
- `POST /tasks` — Create a new task
- `GET /departments` — List all departments

## ❓ Troubleshooting
- If the database fails to start, ensure Docker is running and ports are available.
- For build errors, check Java and Maven versions.

## 📄 License
This project is licensed under the MIT License.
