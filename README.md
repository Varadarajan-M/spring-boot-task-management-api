# Task Manager REST API

A task management backend built with Java, Spring Boot, and PostgreSQL. This is a learning project focused on building production-aware backend skills.

## Stack

- Java 17
- Spring Boot 4.0.5
- PostgreSQL 17 (Docker)
- Maven
- Lombok

## Getting started

### Prerequisites

- JDK 17+
- Docker Desktop

### Run PostgreSQL

```bash
docker compose up -d
```

### Run the application

```bash
./mvnw spring-boot:run
```

The API runs on `http://localhost:8080`.

### Seed test data

```bash
chmod +x seed-data.sh
./seed-data.sh
```

Creates 10 users, 5 projects, and 100 tasks with random assignments.

## Project structure

```
src/main/java/com/learning/taskmanager/
├── controller/          # REST controllers (HTTP layer)
├── service/             # Business logic
├── repository/          # Data access (JPA repositories)
├── entity/              # Database entities
├── dto/                 # Request/response data transfer objects
├── enums/               # TaskStatus, ProjectStatus
├── exception/           # Global exception handler, custom exceptions
└── specification/       # JPA Specifications for dynamic filtering
```

## Entities and relationships

### Task

- `id` (UUID, PK)
- `title`, `description`
- `status` (TODO, IN_PROGRESS, COMPLETED, BACKLOG)
- `createdAt`, `updatedAt` (auto-managed)
- `project` (ManyToOne → Project, optional)
- `assignedTo` (ManyToOne → User, optional)

### User

- `id` (UUID, PK)
- `name`, `email` (unique)
- `createdAt`, `updatedAt`

### Project

- `id` (UUID, PK)
- `name`, `description`
- `status` (PLANNING, ACTIVE, COMPLETED, ON_HOLD, ARCHIVED)
- `startDate`, `endDate`
- `createdAt`, `updatedAt`

## API endpoints

### Tasks

| Method | Endpoint          | Description                        |
| ------ | ----------------- | ---------------------------------- |
| GET    | `/api/tasks`      | List tasks (paginated, filterable) |
| GET    | `/api/tasks/{id}` | Get task by ID                     |
| POST   | `/api/tasks`      | Create task                        |
| PATCH  | `/api/tasks/{id}` | Update task (partial)              |
| DELETE | `/api/tasks/{id}` | Delete task                        |

**Query parameters for GET /api/tasks:**

| Param        | Type       | Description                                        |
| ------------ | ---------- | -------------------------------------------------- |
| `status`     | TaskStatus | Filter by status                                   |
| `projectId`  | UUID       | Filter by project                                  |
| `assigneeId` | UUID       | Filter by assigned user                            |
| `page`       | int        | Page number (default: 0)                           |
| `size`       | int        | Page size (default: 20)                            |
| `sort`       | string     | Sort field and direction (default: createdAt,desc) |

**Create task request:**

```json
{
  "title": "Build login page",
  "description": "Implement OAuth login flow",
  "projectId": "uuid-here",
  "assignedToId": "uuid-here"
}
```

### Users

| Method | Endpoint          | Description           |
| ------ | ----------------- | --------------------- |
| GET    | `/api/users`      | List all users        |
| GET    | `/api/users/{id}` | Get user by ID        |
| POST   | `/api/users`      | Create user           |
| PATCH  | `/api/users/{id}` | Update user (partial) |
| DELETE | `/api/users/{id}` | Delete user           |

### Projects

| Method | Endpoint             | Description              |
| ------ | -------------------- | ------------------------ |
| GET    | `/api/projects`      | List all projects        |
| GET    | `/api/projects/{id}` | Get project by ID        |
| POST   | `/api/projects`      | Create project           |
| PATCH  | `/api/projects/{id}` | Update project (partial) |
| DELETE | `/api/projects/{id}` | Delete project           |

## What's implemented

- **CRUD operations** for tasks, users, and projects
- **Layered architecture**: Controller → Service → Repository
- **DTOs**: Separate Create, Update, and Response DTOs per entity
- **Response shaping**: Summary DTOs for nested relationships (e.g. task response includes project name and assignee info, not full entities)
- **Global exception handling**: Structured JSON error responses with proper HTTP status codes (400, 404, 405)
- **Input validation**: Jakarta Bean Validation annotations (`@NotBlank`, `@Email`, `@Size`) with `@Valid`
- **Dynamic filtering**: JPA Specifications for optional query parameters on task listing
- **Pagination and sorting**: Spring `Pageable` with configurable defaults
- **N+1 query prevention**: `@EntityGraph` on task queries to JOIN FETCH relationships in a single query
- **PATCH semantics**: Partial updates — only sent fields are modified

## What's next

- [ ] ProjectMember entity (User ↔ Project many-to-many with role and joinedAt)
- [ ] Project member endpoints (add/remove/list members)
- [ ] JWT authentication and authorization
- [ ] Scheduled tasks (cron jobs)
- [ ] Email notifications
- [ ] Package-by-feature restructure

## Configuration

Application properties (`src/main/resources/application.properties`):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanager
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
```
