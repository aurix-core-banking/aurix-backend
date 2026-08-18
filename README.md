# Aurix Backend

Plataforma de core banking com 14 microsserviços Java 25 + Spring Boot 4.1.0 + Spring Cloud 2025.1.2. Monorepo Maven multi-module.

## Stack

- **Java 25**, **Spring Boot 4.1.0**, **Spring Cloud 2025.1.2**
- **Maven** multi-module (wrapper: `./mvnw`)
- **PostgreSQL 15** (single shared DB `aurix_db`)
- **Kafka** (61+ topics, naming ADR-0001: `dominio.entidade.versao.versao`)
- **Redis** (cache + rate limiting)
- **Keycloak** (auth + OAuth2)
- **Temporal** (workflow orchestration)
- **Vault** (secrets management)

## Microsserviços

| Módulo | Descrição | Porta |
|---|---|---|
| `aurix-shared` | Library JAR: entidades JPA, DTOs, eventos, cache, crypto, tenant | — |
| `aurix-gateway` | API Gateway com API key + rate limiting + circuit breaker | 8080 |
| `svc-banking` | Core bancário: contas, transações, poupança, salário, pricing, settlement | 8200 |
| `svc-payments` | Processamento PIX | 8201 |
| `svc-credit` | Crédito, empréstimos, financiamento, garantias | 8082 |
| `svc-products` | Catálogo de produtos, elegibilidade, tarifas | 8084 |
| `svc-customer` | Onboarding (PF/PJ), KYC, auth, JWT, MFA | 8083 |
| `svc-fraud` | Detecção de fraude (Kafka-only, sem REST) | 8207 |
| `svc-compliance` | Compliance regulatória (COAF, AML, PEP) | 8205 |
| `svc-finance-mgmt` | Contabilidade, gestão financeira | 8089 |
| `svc-platform` | Open Finance, webhooks, notificações, auditoria | 8092 |
| `svc-intelligence` | Analytics, BI, chatbot, ML fraud | 8091 |
| `svc-cambio` | Câmbio, SPI/STR BACEN | 8093 |
| `svc-cards` | Cartões de crédito/débito | 8094 |
| `svc-ai` | IA/ML integration (LLM, RAG, tools) | 8206 |
| `svc-contracts` | Gestão de contratos, assinatura digital, templates | 8085 |

## Arquitetura

```
Controller → Service → Repository (JPA)
     ↓
  EventHub (Kafka) → Outbox Pattern → OutboxRelay
     ↓
  Temporal Workflows (onboarding PF/PJ, empréstimo consignado)
```

### Padrões críticos

- **Single DB**: Todos os serviços compartilham `aurix_db`
- **Shared entities**: `aurix-shared` com `@EntityScan` + `@EnableJpaRepositories`
- **Provider/Stub pattern**: Integrações externas (Bureau, ClearSale, Quod) com stubs para testes
- **Read/write splitting**: `RoutingDataSource` + `@ReadOnly` annotation + AOP
- **PII encryption**: AES-256-GCM para CPF/CNPJ via `@PiiColumn`
- **Async audit**: `AsyncAuditService` via Kafka (não bloqueia transação)

## Development

```bash
# Build completo
./mvnw clean install

# Build sem testes
./mvnw clean install -DskipTests

# Rodar serviço específico
./mvnw spring-boot:run -pl svc-banking

# Testes
./mvnw test -pl svc-banking

# Docker build
docker build -f Dockerfile.svc --build-arg SVC_NAME=svc-banking -t svc-banking .
```

## Segurança

- **API Keys**: DB-backed com hash SHA-256, rotação, revogação (não YAML)
- **Istio mTLS STRICT**: Comunicação inter-service com mutual TLS
- **Kafka SASL/SCRAM-SHA-512**: Autenticação producer/consumer em prod
- **Vault**: HashiCorp Vault para segredos (database, Kafka, BACEN mTLS)
- **BACEN mTLS**: WebClient com SslContext + KeyManagerFactory + TrustManagerFactory

## Relacionados

- [aurix-frontend](https://github.com/aurix-core-banking/aurix-frontend)
- [aurix-infrastructure](https://github.com/aurix-core-banking/aurix-infrastructure)
- [aurix-openfinance](https://github.com/aurix-core-banking/aurix-openfinance)
- [aurix-data-platform](https://github.com/aurix-core-banking/aurix-data-platform)
