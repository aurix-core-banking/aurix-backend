package com.aurix.platform.shared.util;

import java.util.UUID;

public class TraceContext {

    private static final ThreadLocal<TraceContext> CURRENT = new ThreadLocal<>();

    private final String traceId;
    private final String spanId;
    private final String parentSpanId;

    public TraceContext(String traceId, String spanId, String parentSpanId) {
        this.traceId = traceId;
        this.spanId = spanId;
        this.parentSpanId = parentSpanId;
    }

    public static TraceContext start() {
        String traceId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        String spanId = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        TraceContext ctx = new TraceContext(traceId, spanId, null);
        CURRENT.set(ctx);
        return ctx;
    }

    public static TraceContext fromHeaders(String traceId, String parentSpanId) {
        String spanId = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        TraceContext ctx = new TraceContext(traceId, spanId, parentSpanId);
        CURRENT.set(ctx);
        return ctx;
    }

    public TraceContext newChild() {
        String spanId = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        TraceContext child = new TraceContext(this.traceId, spanId, this.spanId);
        CURRENT.set(child);
        return child;
    }

    public static TraceContext current() {
        return CURRENT.get();
    }

    public static void clear() {
        CURRENT.remove();
    }

    public String getTraceId() { return traceId; }
    public String getSpanId() { return spanId; }
    public String getParentSpanId() { return parentSpanId; }
}
