# Architecture

## Overview

The Aurix backend follows a microservices architecture built with Spring Boot. Each service is an independent deployable unit with its own database schema, communicating via REST (sync) and Kafka (async).

## Modules

- **aurix-shared** — shared domain models, DTOs, and utilities used across services
- **svc-customer** — customer lifecycle management (onboarding, KYC, profile)
- *svc-account* — account management (balances, limits, statements)
- *svc-transaction* — transaction processing (payments, transfers, reconciliation)
- *svc-notification* — notification dispatch (email, SMS, push)

## Communication

- **Synchronous**: REST APIs defined in [aurix-api-specs](https://github.com/aureus-platform/aurix-api-specs)
- **Asynchronous**: Apache Kafka for domain events and CQRS-style reads

## Database

- PostgreSQL per service (database-per-service pattern)
- Flyway for schema migrations

## Security

- OAuth2 / JWT via Spring Security
- Role-based access control (RBAC)
- Service-to-service via mTLS
