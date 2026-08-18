package com.aurix.platform.credit.temporal;

import com.aurix.platform.credit.temporal.activity.EmprestimoConsignadoActivitiesImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmprestimoTemporalWorker {

    private static final Logger log = LoggerFactory.getLogger(EmprestimoTemporalWorker.class);
    private static final String TASK_QUEUE = "credito";

    @Value("${temporal.connection.target:localhost:7233}")
    private String temporalAddress;

    @Value("${temporal.connection.namespace:aurix}")
    private String namespace;

    private WorkerFactory workerFactory;
    private final EmprestimoConsignadoActivitiesImpl activities;

    public EmprestimoTemporalWorker(EmprestimoConsignadoActivitiesImpl activities) {
        this.activities = activities;
    }

    @PostConstruct
    public void iniciar() {
        try {
            WorkflowServiceStubs serviceStub = WorkflowServiceStubs.newConnectedServiceStubsBuilder()
                    .setTarget(temporalAddress)
                    .build();

            WorkflowClient client = WorkflowClient.newInstance(serviceStub,
                    WorkflowClient.Options.newBuilder().setNamespace(namespace).build());

            workerFactory = WorkerFactory.newInstance(client);

            Worker worker = workerFactory.newWorkerBuilder(TASK_QUEUE)
                    .setActivityImplementations(activities)
                    .build();
            worker.registerWorkflowImplementationTypes(EmprestimoConsignadoWorkflowImpl.class);

            workerFactory.start();
            log.info("Temporal worker de crédito iniciado na task queue: {}", TASK_QUEUE);
        } catch (Exception e) {
            log.warn("Falha ao conectar com Temporal ({}): worker não iniciado", e.getMessage());
        }
    }

    @PreDestroy
    public void parar() {
        if (workerFactory != null) {
            workerFactory.shutdown();
        }
    }
}
