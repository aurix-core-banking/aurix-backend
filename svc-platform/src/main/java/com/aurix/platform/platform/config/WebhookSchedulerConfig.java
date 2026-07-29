package com.aurix.platform.platform.config;

import com.aurix.platform.platform.service.WebhookSenderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WebhookSchedulerConfig {
    private final WebhookSenderService webhookSenderService;

    @Scheduled(fixedDelayString = "${aurix.webhooks.retry-interval-ms:60000}")
    public void retryPendentes() {
        webhookSenderService.retryPendentes();
    }

    @java.lang.SuppressWarnings("all")
    public WebhookSchedulerConfig(final WebhookSenderService webhookSenderService) {
        this.webhookSenderService = webhookSenderService;
    }
}
