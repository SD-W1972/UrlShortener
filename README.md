# UrlShortener

A high-performance URL shortening service built with **Spring Boot 3.5** and **Java 21**, following the principles of **Hexagonal Architecture**. This project features real-time analytics, geo-location tracking, and a secure administrative dashboard.

## Features

- **URL Shortening**: Generate unique, short slugs for long URLs using Base62 encoding.
- **Fast Redirection**: Sub-millisecond redirection from short slugs to original URLs.
- **Real-time Analytics**: Tracks every click with detailed information:
  - **IP Address** & **User-Agent** (Device, OS, Browser).
  - **Geographic Location** (Country, City) via MaxMind GeoIP2.
- **Admin Dashboard**: Secure endpoints to monitor URL performance and view aggregated data.
- **Security**: Built-in authentication and role-based access control (RBAC) using Spring Security.
- **Persistence & Caching**: Robust data storage with JPA (PostgreSQL/H2) and high-speed caching with Redis.

## Architecture

The project is structured using **Hexagonal Architecture** (also known as Ports and Adapters), ensuring that the core business logic is isolated from external infrastructure and frameworks.

- **Domain Layer**: Contains core business models (`Url`, `User`, `Click`) and port interfaces (`UrlRepository`, `AnalyticsProvider`, etc.).
- **Application Layer**: Implements business use cases (`ShortenUrlService`, `GetOriginalUrlService`) and coordinates flow.
- **Infrastructure Layer**: Contains technical details such as REST controllers, database adapters (JPA), security configurations, and external integrations (GeoIP).

### Architecture Diagram
![Hexagonal Architecture](./HexagonalArchitecture.svg)

### Business Process
![Business Process Model](./diagram.svg)

## Tech Stack

- **Core**: Java 21, Spring Boot 3.5.13
- **Security**: Spring Security (BCrypt, Basic Auth)
- **Data**: Spring Data JPA, PostgreSQL, H2 (In-memory)
- **Caching**: Spring Data Redis
- **Analytics**: MaxMind GeoIP2, UA Parser
- **Monitoring**: Spring Boot Actuator, Micrometer Prometheus
- **Testing**: JUnit 5, Mockito, LogCaptor

## Getting Started

### Prerequisites
- **Java 21** or higher
- **Maven 3.6+**
- **Redis** (Required for caching and analytics)
- **PostgreSQL** (Optional, defaults to H2 in-memory)

### Installation & Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/secon/UrlShortener.git
   cd UrlShortener
   ```

2. **Start Redis**
   You can run Redis locally or via Docker:
   ```bash
   docker run -d -p 6379:6379 redis:7
   ```

3. **Build and Run**
   ```bash
   ./mvnw spring-boot:run
   ```
   The application will be available at `http://localhost:8080`.

## API Reference

### Public Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/shorten` | Shortens a long URL. Body: `String` (URL). |
| `GET` | `/{slug}` | Redirects to original URL and records analytics. |
| `POST` | `/register` | Registers a new user. Body: `{"email": "...", "password": "..."}`. |
| `POST` | `/login` | Authenticates a user. Body: `{"email": "...", "password": "..."}`. |

### Admin Endpoints (Requires `ADMIN` role)

Default credentials for testing: `admin@test.com` / `admin123`

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/admin/dashboard` | Get analytics for a specific URL (`?originalUrl=...`). |
| `GET` | `/api/admin/dashboard/all` | Get aggregated analytics for all URLs. |

## Testing

The project follows TDD principles and uses JUnit 5 for unit and integration testing.
```bash
./mvnw test
```

## Monitoring

The application exposes metrics for Prometheus and health checks:
- **Health**: `http://localhost:8080/actuator/health`
- **Prometheus**: `http://localhost:8080/actuator/prometheus`
- **H2 Console**: `http://localhost:8080/admin/h2-console` (Access at `/admin/h2-console`)

---