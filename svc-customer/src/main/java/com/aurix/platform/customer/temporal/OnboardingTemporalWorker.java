package com.aurix.platform.customer.temporal;

import com.aurix.platform.customer.temporal.activity.OnboardingPFActivitiesImpl;
import com.aurix.platform.customer.temporal.activity.OnboardingPJActivitiesImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OnboardingTemporalWorker {

    private static final Logger log = LoggerFactory.getLogger(OnboardingTemporalWorker.class);
    private static final String TASK_QUEUE = "onboarding";

    @Value("${temporal.connection.target:localhost:7233}")
    private String temporalAddress;

    @Value("${temporal.connection.namespace:aurix}")
    private String namespace;

    private WorkerFactory workerFactory;

    private final OnboardingPFActivitiesImpl pfActivities;
    private final OnboardingPJActivitiesImpl pjActivities;

    public OnboardingTemporalWorker(OnboardingPFActivitiesImpl pfActivities,
                                     OnboardingPJActivitiesImpl pjActivities) {
        this.pfActivities = pfActivities;
        this.pjActivities = pjActivities;
    }

    @PostConstruct
    public void iniciar() {
        try {
            WorkflowServiceStubsOptions stubsOptions = WorkflowServiceStubsOptions.newBuilder()
                    .setTarget(temporalAddress)
                    .build();
            WorkflowServiceStubs serviceStubs = WorkflowServiceStubs.newServiceStubs(stubsOptions);

            WorkflowClient workflowClient = WorkflowClient.newInstance(serviceStubs,
                    WorkflowClientOptions.newBuilder().setNamespace(namespace).build());

            workerFactory = WorkerFactory.newInstance(workflowClient);

            Worker worker = workerFactory.newWorker(TASK_QUEUE);
            worker.registerWorkflowImplementationTypes(OnboardingPFWorkflowImpl.class, OnboardingPJWorkflowImpl.class);
            worker.registerActivitiesImplementations(pfActivities, pjActivities);

            workerFactory.start();
            log.info("Temporal workers iniciados na task queue: {}", TASK_QUEUE);
        } catch (Exception e) {
            log.warn("Falha ao conectar com Temporal ({}): workers não iniciados", e.getMessage());
        }
    }

    @PreDestroy
    public void parar() {
        if (workerFactory != null) {
            workerFactory.shutdown();
            log.info("Temporal workers encerrados");
        }
    }
}
