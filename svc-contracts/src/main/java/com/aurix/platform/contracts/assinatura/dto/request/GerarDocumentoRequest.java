package com.aurix.platform.contracts.assinatura.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GerarDocumentoRequest {

    @NotBlank(message = "Documento do cliente é obrigatório")
    @Size(max = 14, message = "Documento deve ter no máximo 14 caracteres")
    private String clienteDocumento;

    private String clienteNome;

    public GerarDocumentoRequest() {}

    public GerarDocumentoRequest(String clienteDocumento, String clienteNome) {
        this.clienteDocumento = clienteDocumento;
        this.clienteNome = clienteNome;
    }

    public String getClienteDocumento() { return clienteDocumento; }
    public void setClienteDocumento(String clienteDocumento) { this.clienteDocumento = clienteDocumento; }
    public String getClienteNome() { return clienteNome; }
    public void setClienteNome(String clienteNome) { this.clienteNome = clienteNome; }
}
