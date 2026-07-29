# AURIX Backend

Backend da plataforma AURIX Core Banking — Java 25 + Spring Boot 4.1.

## Estrutura

Projeto Maven multi-módulo com **13 domínios consolidados** + 2 infra:

```
aurix-platform/apps/backend/
├── aurix-shared/       # DTOs, configs, utilitários compartilhados
├── aurix-gateway/      # API Gateway (Spring Cloud Gateway)
├── svc-payments/        # Core bancário, contas, PIX, boletos (8081)
├── svc-credit/          # Crédito, consignado, financiamento (8082)
├── svc-customer/        # Clientes, KYC, onboarding (8083)
├── svc-products/        # Poupança, salário, investimento (8084)
├── svc-finance-mgmt/    # Contabilidade, orçamento, impostos (8089)
├── svc-intelligence/    # Analytics, BI, ML (8091)
├── svc-platform/        # Open Finance, BaaS, webhooks (8092)
├── svc-cambio/          # Câmbio, BACEN, SPI/STR (8093)
├── svc-cards/           # Cartões crédito/débito (8094)
├── svc-banking/         # Organização, empresas (8095)
├── svc-compliance/      # Regulação, AML (8205)
├── svc-fraud/           # Detecção de fraude (8204)
└── svc-ai/              # IA, LLM, agentes (8206)
```

## Como Executar

### Pré-requisitos
- Java 25+
- Maven 3.9+
- Docker (para dependências como Postgres, Redis, etc)

### Comandos Recomendados (via Makefile Raiz)
```bash
# Compilar todos os módulos
make build-backend

# Executar todos os testes
make test-backend
```

### Comandos Diretos (Maven)
```bash
# Build completo
mvn clean install -DskipTests

# Testes de um módulo
mvn test -pl svc-payments

# Compilar sem testes
mvn clean compile -DskipTests
```

## Configuração

Cada serviço possui seu próprio `application.yml` em `src/main/resources`. 
As configurações globais de infraestrutura são injetadas via perfis Spring (`dev`, `prod`).

### Portas dos Serviços

| Serviço | Porta | Context Path |
|---------|-------|-------------|
| Gateway | 8080 | `/` |
| svc-payments | 8081 | `/api/core`, `/api/pix` |
| svc-credit | 8082 | `/api/credit` |
| svc-customer | 8083 | `/api/customer` |
| svc-products | 8084 | `/api/products` |
| svc-finance-mgmt | 8089 | `/api/finance` |
| svc-intelligence | 8091 | `/api/intelligence` |
| svc-platform | 8092 | `/api/platform` |
| svc-cambio | 8093 | `/api/cambio` |
| svc-cards | 8094 | `/api/cards` |
| svc-banking | 8095 | `/api/banking` |
| svc-fraud | 8204 | `/api/fraud` |
| svc-compliance | 8205 | `/api/compliance` |
| svc-ai | 8206 | `/api/ai` |

---
**Status**: Java 25 | Spring Boot 4.1 | 13 Domain Services
