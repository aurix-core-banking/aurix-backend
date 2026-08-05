package com.aurix.platform.intelligence.service;

import com.aurix.platform.intelligence.repository.MetricaRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Profile("prod")
public class BiServiceProd implements BiService {

    private final MetricaRepository metricaRepository;

    public BiServiceProd(MetricaRepository metricaRepository) {
        this.metricaRepository = metricaRepository;
    }

    @Override
    public Map<String, Object> obterKpis() {
        long totalMetricas = metricaRepository.count();
        Map<String, Object> kpis = new HashMap<>();
        kpis.put("totalMetricas", totalMetricas);
        kpis.put("transacoesHoje", 0);
        kpis.put("volumePixHoje", 0);
        kpis.put("status", "prod-placeholder");
        return kpis;
    }

    @Override
    public Map<String, Object> obterDashboard() {
        return Map.of("kpis", Map.of("metricas", metricaRepository.count()), "alertas", 0, "status", "prod-placeholder");
    }
}
