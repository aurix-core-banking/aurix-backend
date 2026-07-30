package com.aurix.platform.ai.controller.ml;

import com.aurix.platform.ai.client.MlGrpcClient;
import com.aurix.platform.ml.v1.CreditAnalysisRequest;
import com.aurix.platform.ml.v1.CreditAnalysisResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/ml/credit")
@Tag(name = "ML Credit Analysis")
public class CreditController {

    private final MlGrpcClient mlClient;

    public CreditController(MlGrpcClient mlClient) {
        this.mlClient = mlClient;
    }

    @PostMapping("/evaluate")
    @Operation(summary = "Evaluate credit request")
    public CreditAnalysisResponse evaluate(@RequestBody CreditAnalysisRequest request) {
        return mlClient.evaluateCredit(request);
    }
}
