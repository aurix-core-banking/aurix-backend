package com.aurix.platform.banking.integration.webhook;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WebhookRetryScheduler {

    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WebhookRetryScheduler.class);
    private final EventPipelineService eventPipelineService;

    public WebhookRetryScheduler(final EventPipelineService eventPipelineService) {
        this.eventPipelineService = eventPipelineService;
    }

    @Scheduled(fixedDelayString = "${aurix.webhooks.retry-interval-ms:30000}")
    public void retryFailedWebhooks() {
        int count = eventPipelineService.retryFailed();
        if (count > 0) {
            log.info("Retry scheduler processed {} webhook events", count);
        }
    }
}
