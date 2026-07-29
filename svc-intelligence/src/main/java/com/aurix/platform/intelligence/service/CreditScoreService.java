package com.aurix.platform.intelligence.service;

import java.util.Map;

public interface CreditScoreService {
    Map<String, Object> obterScore(String clienteId);
}
