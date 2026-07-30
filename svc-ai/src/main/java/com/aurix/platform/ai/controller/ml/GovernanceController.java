package com.aurix.platform.ai.controller.ml;

import com.aurix.platform.ai.client.MlGrpcClient;
import com.aurix.platform.ml.v1.GovernanceDecisionRequest;
import com.aurix.platform.ml.v1.GovernanceDecisionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/ml/governance")
@Tag(name = "ML Governance (R1/R2/R3)")
public class GovernanceController {

    private final MlGrpcClient mlClient;

    public GovernanceController(MlGrpcClient mlClient) {
        this.mlClient = mlClient;
    }

    @PostMapping("/decide")
    @Operation(summary = "Make a governed decision (R1/R2/R3)")
    public GovernanceDecisionResponse decide(@RequestBody GovernanceDecisionRequest request) {
        return mlClient.decide(request);
    }
}
