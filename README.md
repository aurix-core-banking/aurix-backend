# Aurix Backend

Core banking backend services for the Aurix platform.

## Structure

The backend is a Maven multi-module project (`apps/backend/pom.xml`) containing microservices and shared libraries. Each module is a Spring Boot application or a shared library deployed independently.

### Modules

- **aurix-shared** — shared domain models, DTOs, utilities
- **svc-customer** — customer management service
- *svc-account* — account management service (planned)
- *svc-transaction* — transaction processing service (planned)

## Tech Stack

- Java 21
- Spring Boot / Spring Cloud
- Maven
- PostgreSQL
- Kafka (messaging)

## Development

```bash
./mvnw compile
./mvnw test
./mvnw spring-boot:run -pl svc-customer
```

## Related

- [aurix-core-banking](https://github.com/aureus-platform/aurix-core-banking) — monorepo
- [aurix-frontend](https://github.com/aureus-platform/aurix-frontend)

