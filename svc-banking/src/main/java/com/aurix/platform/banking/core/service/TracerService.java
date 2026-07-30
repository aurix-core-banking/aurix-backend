package com.aurix.platform.banking.core.service;

import com.aurix.platform.shared.util.TraceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class TracerService {

    private static final Logger log = LoggerFactory.getLogger("TRACER");

    public TraceContext startTrace() {
        TraceContext ctx = TraceContext.start();
        MDC.put("traceId", ctx.getTraceId());
        MDC.put("spanId", ctx.getSpanId());
        log.info("Trace started: traceId={}", ctx.getTraceId());
        return ctx;
    }

    public TraceContext continueTrace(String traceId, String parentSpanId) {
        TraceContext ctx = TraceContext.fromHeaders(traceId, parentSpanId);
        MDC.put("traceId", ctx.getTraceId());
        MDC.put("spanId", ctx.getSpanId());
        MDC.put("parentSpanId", ctx.getParentSpanId());
        return ctx;
    }

    public Span createSpan(String operationName) {
        TraceContext ctx = TraceContext.current();
        if (ctx == null) {
            ctx = startTrace();
        }
        TraceContext childCtx = ctx.newChild();
        MDC.put("spanId", childCtx.getSpanId());
        return new Span(operationName, childCtx);
    }

    public void endTrace() {
        TraceContext.clear();
        MDC.clear();
    }

    public class Span implements AutoCloseable {
        private final String operationName;
        private final TraceContext context;
        private final Instant start;
        private final Map<String, String> tags = new LinkedHashMap<>();

        Span(String operationName, TraceContext context) {
            this.operationName = operationName;
            this.context = context;
            this.start = Instant.now();
            log.info("[{}] span started: traceId={}, spanId={}", operationName, context.getTraceId(), context.getSpanId());
        }

        public Span tag(String key, String value) {
            tags.put(key, value);
            return this;
        }

        @Override
        public void close() {
            Duration elapsed = Duration.between(start, Instant.now());
            log.info("[{}] span finished: traceId={}, spanId={}, duration={}ms, tags={}",
                    operationName, context.getTraceId(), context.getSpanId(), elapsed.toMillis(), tags);
        }
    }
}
