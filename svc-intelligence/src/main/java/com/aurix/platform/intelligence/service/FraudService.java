package com.aurix.platform.intelligence.service;

import java.util.Map;

public interface FraudService {
    Map<String, Object> avaliarFraude(Map<String, Object> transacao);
}
