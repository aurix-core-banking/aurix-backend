package com.aurix.platform.banking.dto;

import com.aurix.platform.banking.entity.Workflow;
import java.time.LocalDateTime;

public class WorkflowDTO {
    private Long id;
    private String codigoWorkflow;
    private String nomeWorkflow;
    private String descricao;
    private Workflow.TipoWorkflow tipoWorkflow;
    private Workflow.StatusWorkflow status;
    private Long empresaId;
    private String configuracoesWorkflow;
    private String regrasWorkflow;
    private Integer timeoutHoras;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    
    public WorkflowDTO() {}
    
    public WorkflowDTO(Workflow workflow) {
        this.id = workflow.getId();
        this.codigoWorkflow = workflow.getCodigoWorkflow();
        this.nomeWorkflow = workflow.getNomeWorkflow();
        this.descricao = workflow.getDescricao();
        this.tipoWorkflow = workflow.getTipoWorkflow();
        this.status = workflow.getStatus();
        this.empresaId = workflow.getEmpresa().getId();
        this.configuracoesWorkflow = workflow.getConfiguracoesWorkflow();
        this.regrasWorkflow = workflow.getRegrasWorkflow();
        this.timeoutHoras = workflow.getTimeoutHoras();
        this.dataCriacao = workflow.getDataCriacao();
        this.dataAtualizacao = workflow.getDataAtualizacao();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCodigoWorkflow() { return codigoWorkflow; }
    public void setCodigoWorkflow(String codigoWorkflow) { this.codigoWorkflow = codigoWorkflow; }
    
    public String getNomeWorkflow() { return nomeWorkflow; }
    public void setNomeWorkflow(String nomeWorkflow) { this.nomeWorkflow = nomeWorkflow; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public Workflow.TipoWorkflow getTipoWorkflow() { return tipoWorkflow; }
    public void setTipoWorkflow(Workflow.TipoWorkflow tipoWorkflow) { this.tipoWorkflow = tipoWorkflow; }
    
    public Workflow.StatusWorkflow getStatus() { return status; }
    public void setStatus(Workflow.StatusWorkflow status) { this.status = status; }
    
    public Long getEmpresaId() { return empresaId; }
    public void setEmpresaId(Long empresaId) { this.empresaId = empresaId; }
    
    public String getConfiguracoesWorkflow() { return configuracoesWorkflow; }
    public void setConfiguracoesWorkflow(String configuracoesWorkflow) { this.configuracoesWorkflow = configuracoesWorkflow; }
    
    public String getRegrasWorkflow() { return regrasWorkflow; }
    public void setRegrasWorkflow(String regrasWorkflow) { this.regrasWorkflow = regrasWorkflow; }
    
    public Integer getTimeoutHoras() { return timeoutHoras; }
    public void setTimeoutHoras(Integer timeoutHoras) { this.timeoutHoras = timeoutHoras; }
    
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
}
