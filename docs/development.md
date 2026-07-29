# Development

## Prerequisites

- Java 21 (Eclipse Temurin recommended)
- Maven 3.9+
- PostgreSQL 16+
- Docker & Docker Compose

## Setup

```bash
# Start dependencies (PostgreSQL, Kafka)
docker compose -f docker-compose.dev.yml up -d

# Build all modules
./mvnw clean compile

# Run tests
./mvnw test

# Run a specific service
./mvnw spring-boot:run -pl svc-customer
```

## Project Structure

```
apps/backend/
├── pom.xml                  # Parent POM
├── mvnw / mvnw.cmd          # Maven Wrapper
├── aurix-shared/            # Shared library
│   ├── pom.xml
│   └── src/
├── svc-customer/            # Customer microservice
│   ├── pom.xml
│   └── src/
└── docker-compose.dev.yml   # Dev infrastructure
```

## Code Style

- Follow standard Java conventions
- Use Lombok for boilerplate reduction
- Write tests using JUnit 5 + Mockito
- Keep controllers thin; business logic in services
