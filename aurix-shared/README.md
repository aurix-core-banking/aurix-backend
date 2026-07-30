# AURIX Shared

Biblioteca compartilhada do AURIX: entidades, DTOs, eventos e integracao entre modulos.

## Conteudo

- Entidades JPA compartilhadas (Conta, Transacao, Cliente, BaseEntity, etc.)
- DTOs e mapeamentos
- Publicacao e consumo de eventos (Kafka)
- Servicos de integracao e cache compartilhado (Redis)

## Integracao entre modulos

Documentacao de IntegrationService, EventPublisher, EventListener, SharedCacheService e APIs: [README-INTEGRATION.md](./README-INTEGRATION.md).

## Uso

Este modulo e dependencia de outros modulos backend. Nao possui aplicacao Spring Boot propria; e incluido como `dependency` nos demais servicos.
