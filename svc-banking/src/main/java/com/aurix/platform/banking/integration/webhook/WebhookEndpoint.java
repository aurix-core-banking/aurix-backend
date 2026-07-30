package com.aurix.platform.banking.integration.webhook;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "aurix.webhooks")
public class WebhookEndpoint {
    
    private List<Endpoint> endpoints;
    
    public List<Endpoint> getEndpoints() {
        return endpoints;
    }
    
    public void setEndpoints(List<Endpoint> endpoints) {
        this.endpoints = endpoints;
    }
    
    public static class Endpoint {
        private String url;
        private String apiKey;
        private boolean enabled;
        private List<String> eventos;
        
        public String getUrl() {
            return url;
        }
        
        public void setUrl(String url) {
            this.url = url;
        }
        
        public String getApiKey() {
            return apiKey;
        }
        
        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }
        
        public boolean isEnabled() {
            return enabled;
        }
        
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
        
        public List<String> getEventos() {
            return eventos;
        }
        
        public void setEventos(List<String> eventos) {
            this.eventos = eventos;
        }
    }
}
