package com.aurix.platform.ai.controller.ml;

import com.aurix.platform.ai.client.MlGrpcClient;
import com.aurix.platform.ml.v1.ComplianceCheckRequest;
import com.aurix.platform.ml.v1.ComplianceCheckResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/ml/compliance")
@Tag(name = "ML Compliance Check")
public class ComplianceController {

    private final MlGrpcClient mlClient;

    public ComplianceController(MlGrpcClient mlClient) {
        this.mlClient = mlClient;
    }

    @PostMapping("/check")
    @Operation(summary = "Check regulatory compliance")
    public ComplianceCheckResponse check(@RequestBody ComplianceCheckRequest request) {
        return mlClient.checkCompliance(request);
    }
}
