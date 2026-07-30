package com.aurix.platform.ai.client;

import com.aurix.platform.ml.v1.*;

import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class MlGrpcClient {

    private static final Logger log = LoggerFactory.getLogger(MlGrpcClient.class);

    @Value("${aurix.ml.grpc.host:localhost}")
    private String host;

    @Value("${aurix.ml.grpc.port:50051}")
    private int port;

    @Value("${aurix.ml.grpc.timeout-seconds:10}")
    private int timeout;

    private ManagedChannel channel;
    private FraudDetectionServiceGrpc.FraudDetectionServiceBlockingStub fraudStub;
    private CreditAnalysisServiceGrpc.CreditAnalysisServiceBlockingStub creditStub;
    private ComplianceCheckServiceGrpc.ComplianceCheckServiceBlockingStub complianceStub;
    private GovernanceServiceGrpc.GovernanceServiceBlockingStub governanceStub;

    @PostConstruct
    public void init() {
        channel = Grpc.newChannelBuilder(
                String.format("%s:%d", host, port),
                InsecureChannelCredentials.create()
        ).build();
        fraudStub = FraudDetectionServiceGrpc.newBlockingStub(channel);
        creditStub = CreditAnalysisServiceGrpc.newBlockingStub(channel);
        complianceStub = ComplianceCheckServiceGrpc.newBlockingStub(channel);
        governanceStub = GovernanceServiceGrpc.newBlockingStub(channel);
        log.info("gRPC client connected to {}:{}", host, port);
    }

    public FraudAnalysisResponse analyzeTransaction(FraudAnalysisRequest request) {
        return fraudStub.withDeadlineAfter(timeout, TimeUnit.SECONDS).analyzeTransaction(request);
    }

    public CreditAnalysisResponse evaluateCredit(CreditAnalysisRequest request) {
        return creditStub.withDeadlineAfter(timeout, TimeUnit.SECONDS).evaluateCredit(request);
    }

    public ComplianceCheckResponse checkCompliance(ComplianceCheckRequest request) {
        return complianceStub.withDeadlineAfter(timeout, TimeUnit.SECONDS).checkCompliance(request);
    }

    public GovernanceDecisionResponse decide(GovernanceDecisionRequest request) {
        return governanceStub.withDeadlineAfter(timeout, TimeUnit.SECONDS).decide(request);
    }

    @PreDestroy
    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            try {
                channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                channel.shutdownNow();
            }
        }
    }
}
