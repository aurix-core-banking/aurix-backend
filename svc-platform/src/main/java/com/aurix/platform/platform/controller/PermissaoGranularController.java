package com.aurix.platform.platform.controller;

import com.aurix.platform.shared.entity.PermissaoGranular;
import com.aurix.platform.platform.service.PermissaoGranularService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/platform/permissoes")
@Tag(name = "Permissões Granulares", description = "RBAC avançado com recurso, ação, condição e escopo")
public class PermissaoGranularController {
    private final PermissaoGranularService service;

    @PostMapping
    @Operation(summary = "Criar permissão granular")
    public ResponseEntity<PermissaoGranular> criar(@RequestBody CriarPermissaoRequest request) {
        PermissaoGranular p = service.criar(request.getRoleId(), request.getRecurso(), request.getAcao(), request.getEscopo(), request.getCondicao(), request.getDescricao());
        return ResponseEntity.ok(p);
    }

    @GetMapping("/role/{roleId}")
    @Operation(summary = "Listar permissões por role")
    public ResponseEntity<List<PermissaoGranular>> listarPorRole(@PathVariable Long roleId) {
        return ResponseEntity.ok(service.listarPorRole(roleId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar permissão granular")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        service.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verificar")
    @Operation(summary = "Verificar se role pode acessar recurso/ação/escopo")
    public ResponseEntity<Map<String, Boolean>> verificar(@RequestParam Long roleId, @RequestParam String recurso, @RequestParam String acao, @RequestParam(defaultValue = "propria") String escopo) {
        boolean pode = service.podeAcessar(roleId, recurso, acao, escopo);
        return ResponseEntity.ok(Map.of("podeAcessar", pode));
    }


    public static class CriarPermissaoRequest {
        private Long roleId;
        private String recurso;
        private String acao;
        private String escopo;
        private String condicao;
        private String descricao;

        @java.lang.SuppressWarnings("all")
        public CriarPermissaoRequest() {
        }

        @java.lang.SuppressWarnings("all")
        public Long getRoleId() {
            return this.roleId;
        }

        @java.lang.SuppressWarnings("all")
        public String getRecurso() {
            return this.recurso;
        }

        @java.lang.SuppressWarnings("all")
        public String getAcao() {
            return this.acao;
        }

        @java.lang.SuppressWarnings("all")
        public String getEscopo() {
            return this.escopo;
        }

        @java.lang.SuppressWarnings("all")
        public String getCondicao() {
            return this.condicao;
        }

        @java.lang.SuppressWarnings("all")
        public String getDescricao() {
            return this.descricao;
        }

        @java.lang.SuppressWarnings("all")
        public void setRoleId(final Long roleId) {
            this.roleId = roleId;
        }

        @java.lang.SuppressWarnings("all")
        public void setRecurso(final String recurso) {
            this.recurso = recurso;
        }

        @java.lang.SuppressWarnings("all")
        public void setAcao(final String acao) {
            this.acao = acao;
        }

        @java.lang.SuppressWarnings("all")
        public void setEscopo(final String escopo) {
            this.escopo = escopo;
        }

        @java.lang.SuppressWarnings("all")
        public void setCondicao(final String condicao) {
            this.condicao = condicao;
        }

        @java.lang.SuppressWarnings("all")
        public void setDescricao(final String descricao) {
            this.descricao = descricao;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof PermissaoGranularController.CriarPermissaoRequest)) return false;
            final PermissaoGranularController.CriarPermissaoRequest other = (PermissaoGranularController.CriarPermissaoRequest) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            final java.lang.Object this$roleId = this.getRoleId();
            final java.lang.Object other$roleId = other.getRoleId();
            if (this$roleId == null ? other$roleId != null : !this$roleId.equals(other$roleId)) return false;
            final java.lang.Object this$recurso = this.getRecurso();
            final java.lang.Object other$recurso = other.getRecurso();
            if (this$recurso == null ? other$recurso != null : !this$recurso.equals(other$recurso)) return false;
            final java.lang.Object this$acao = this.getAcao();
            final java.lang.Object other$acao = other.getAcao();
            if (this$acao == null ? other$acao != null : !this$acao.equals(other$acao)) return false;
            final java.lang.Object this$escopo = this.getEscopo();
            final java.lang.Object other$escopo = other.getEscopo();
            if (this$escopo == null ? other$escopo != null : !this$escopo.equals(other$escopo)) return false;
            final java.lang.Object this$condicao = this.getCondicao();
            final java.lang.Object other$condicao = other.getCondicao();
            if (this$condicao == null ? other$condicao != null : !this$condicao.equals(other$condicao)) return false;
            final java.lang.Object this$descricao = this.getDescricao();
            final java.lang.Object other$descricao = other.getDescricao();
            if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof PermissaoGranularController.CriarPermissaoRequest;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final java.lang.Object $roleId = this.getRoleId();
            result = result * PRIME + ($roleId == null ? 43 : $roleId.hashCode());
            final java.lang.Object $recurso = this.getRecurso();
            result = result * PRIME + ($recurso == null ? 43 : $recurso.hashCode());
            final java.lang.Object $acao = this.getAcao();
            result = result * PRIME + ($acao == null ? 43 : $acao.hashCode());
            final java.lang.Object $escopo = this.getEscopo();
            result = result * PRIME + ($escopo == null ? 43 : $escopo.hashCode());
            final java.lang.Object $condicao = this.getCondicao();
            result = result * PRIME + ($condicao == null ? 43 : $condicao.hashCode());
            final java.lang.Object $descricao = this.getDescricao();
            result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "PermissaoGranularController.CriarPermissaoRequest(roleId=" + this.getRoleId() + ", recurso=" + this.getRecurso() + ", acao=" + this.getAcao() + ", escopo=" + this.getEscopo() + ", condicao=" + this.getCondicao() + ", descricao=" + this.getDescricao() + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public PermissaoGranularController(final PermissaoGranularService service) {
        this.service = service;
    }
}
