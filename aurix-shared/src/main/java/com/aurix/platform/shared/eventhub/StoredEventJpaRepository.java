package com.aurix.platform.shared.eventhub;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StoredEventJpaRepository extends JpaRepository<StoredEventEntity, Long> {
    List<StoredEventEntity> findByEventTypeOrderByTimestampAsc(String eventType);
    List<StoredEventEntity> findByEventId(String eventId);
    List<StoredEventEntity> findByTimestampBetweenOrderByTimestampAsc(LocalDateTime start, LocalDateTime end);
    List<StoredEventEntity> findBySourceOrderByTimestampAsc(String source);
    List<StoredEventEntity> findByCorrelationIdOrderByTimestampAsc(String correlationId);
}
