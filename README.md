# Employee Task System

A comprehensive REST API system to manage employees, departments, and tasks. Built with **Java 17**, **Quarkus**, and **PostgreSQL**.

## 📋 Project Overview
This application provides a complete task management solution with:
- 👥 **Employee Management** - Create, update, and track employees
- 🏢 **Department Management** - Organize employees by departments
- ✅ **Task Management** - Assign, track, and prioritize tasks
- 📊 **Dashboard & Analytics** - Real-time statistics and insights
- 🔍 **Advanced Filtering** - Search by status, priority, department, and more
- 📝 **RESTful APIs** - Full CRUD operations with Swagger documentation

## 🛠 Prerequisites
- **Java 17+** (JDK 17 or higher)
- **Maven 3.8+**
- **Docker & Docker Compose**
- **Git**
- **Postman** (optional, for API testing)

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/Ajitesh-Gupta/Employee-Task-System.git
cd employee-task-system
```

### 2. Start PostgreSQL Database
```bash
docker-compose up -d
```

### 3. Run Quarkus
```bash
mvn quarkus:dev
```

### 4. Test
Visit: http://localhost:8080/q/swagger-ui

### 5. API Docs
- Swagger UI: http://localhost:8080/q/swagger-ui
- OpenAPI spec: http://localhost:8080/q/openapi

### 6. Test with Postman 📮
[![Run in Postman](https://run.pstmn.io/button.svg)](https://ajitesh-gupta-4266563.postman.co/workspace/ajitesh-gupta%27s-Workspace~89629568-79cc-4e12-87f3-c13e084c8087/collection/49070048-1980c81a-fe53-4928-a01e-91db0ac48a71?action=share&creator=49070048&active-environment=49070048-c38873f9-f983-409f-8fc7-9f6150420ece)

**What's Included:**
- ✅ All API endpoints pre-configured
- ✅ Sample request bodies for POST/PUT operations
- ✅ Environment variables for easy testing
- ✅ Organized by resource (Employees, Tasks, Departments, Dashboard)

**Quick Setup:**
1. Click the "Run in Postman" button above
2. Start testing the APIs immediately!

## 📚 Example API Endpoints
- `GET /api/employees` — List all employees
- `POST /api/tasks` — Create a new task
- `GET /api/departments` — List all departments
- `GET /api/dashboard/stats` — Get overall statistics
- `GET /api/tasks/overdue` — Get overdue tasks

## 📄 License
This project is licensed under the MIT License.
