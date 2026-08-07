package com.aurix.platform.shared.repository;

import com.aurix.platform.shared.entity.AssetRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AssetRateRepository extends JpaRepository<AssetRate, Long> {

    Optional<AssetRate> findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);

    @Query("SELECT r FROM AssetRate r WHERE r.fromCurrency = :from AND r.toCurrency = :to "
           + "AND r.validFrom <= :now AND (r.validUntil IS NULL OR r.validUntil >= :now)")
    Optional<AssetRate> findValidRate(@Param("from") String from, @Param("to") String to,
                                       @Param("now") LocalDateTime now);
}
