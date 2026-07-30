package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.repository.AssetRateRepository;
import com.aurix.platform.shared.entity.AssetRate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AssetRateService {

    private static final int CONVERSION_SCALE = 8;

    private final AssetRateRepository repository;

    public AssetRateService(AssetRateRepository repository) {
        this.repository = repository;
    }

    public BigDecimal convert(BigDecimal amount, String fromCurrency, String toCurrency) {
        if (fromCurrency.equalsIgnoreCase(toCurrency)) {
            return amount;
        }

        Optional<AssetRate> rateOpt = repository.findValidRate(
                fromCurrency.toUpperCase(), toCurrency.toUpperCase(), LocalDateTime.now());

        if (rateOpt.isEmpty()) {
            rateOpt = repository.findValidRate(
                    toCurrency.toUpperCase(), fromCurrency.toUpperCase(), LocalDateTime.now());

            if (rateOpt.isPresent()) {
                AssetRate inverse = rateOpt.get();
                BigDecimal inverseRate = inverse.getInverseRate() != null
                        ? inverse.getInverseRate()
                        : BigDecimal.ONE.divide(inverse.getRate(), CONVERSION_SCALE, RoundingMode.HALF_UP);
                return amount.multiply(inverseRate).setScale(4, RoundingMode.HALF_UP);
            }

            throw new IllegalArgumentException(
                    "No rate found for " + fromCurrency + " -> " + toCurrency);
        }

        AssetRate rate = rateOpt.get();
        return amount.multiply(rate.getRate()).setScale(4, RoundingMode.HALF_UP);
    }
}
