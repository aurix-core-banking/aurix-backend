package com.aurix.platform.customer.security.service;

import com.aurix.platform.shared.entity.PermissaoGranular;
import com.aurix.platform.shared.repository.PermissaoGranularRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.regex.Pattern;

@Service
@SuppressWarnings({"PMD.AvoidBranchingStatementAsLastInLoop", "PMD.CollapsibleIfStatements"})
public class PermissaoGranularService {
    private final PermissaoGranularRepository repository;

    @Transactional(readOnly = true)
    public boolean podeAcessar(Long roleId, String recurso, String acao, String escopoContexto, BigDecimal valorContexto, LocalTime horarioContexto) {
        List<PermissaoGranular> permissoes = repository.findByRoleIdAndRecursoAndAcao(roleId, recurso, acao);
        if (permissoes == null || permissoes.isEmpty()) {
            return false;
        }
        for (PermissaoGranular p : permissoes) {
            if (!escopoPermitido(p.getEscopo(), escopoContexto)) {
                continue;
            }
            if (p.getCondicao() != null && !p.getCondicao().isBlank()) {
                if (!avaliaCondicao(p.getCondicao(), valorContexto, horarioContexto)) {
                    continue;
                }
            }
            return true;
        }
        return false;
    }

    public boolean podeAcessar(Long roleId, String recurso, String acao, String escopoContexto) {
        return podeAcessar(roleId, recurso, acao, escopoContexto, null, null);
    }

    private boolean escopoPermitido(String escopoPermissao, String escopoContexto) {
        if (PermissaoGranular.ESCOPO_BANCO.equals(escopoPermissao)) {
            return true;
        }
        if (PermissaoGranular.ESCOPO_AGENCIA.equals(escopoPermissao)) {
            return PermissaoGranular.ESCOPO_BANCO.equals(escopoContexto) || PermissaoGranular.ESCOPO_AGENCIA.equals(escopoContexto);
        }
        return PermissaoGranular.ESCOPO_PROPRIA.equals(escopoContexto);
    }

    private boolean avaliaCondicao(String condicao, BigDecimal valor, LocalTime horario) {
        if (condicao == null || condicao.isBlank()) {
            return true;
        }
        if (valor != null && condicao.contains("valor")) {
            Pattern p = Pattern.compile("valor\\s*[<>=]+\\s*([\\d.]+)");
            var m = p.matcher(condicao);
            if (m.find()) {
                BigDecimal limite = new BigDecimal(m.group(1));
                if (condicao.contains("<")) return valor.compareTo(limite) < 0;
                if (condicao.contains(">")) return valor.compareTo(limite) > 0;
                if (condicao.contains("=")) return valor.compareTo(limite) == 0;
            }
        }
        if (horario != null && condicao.contains("horario")) {
            Pattern p = Pattern.compile("horario\\s*(\\d{1,2})-(\\d{1,2})");
            var m = p.matcher(condicao);
            if (m.find()) {
                int inicio = Integer.parseInt(m.group(1));
                int fim = Integer.parseInt(m.group(2));
                int h = horario.getHour();
                return h >= inicio && h <= fim;
            }
        }
        return true;
    }

    @Transactional
    public PermissaoGranular criar(Long roleId, String recurso, String acao, String escopo, String condicao, String descricao) {
        if (repository.existsByRoleIdAndRecursoAndAcaoAndEscopo(roleId, recurso, acao, escopo)) {
            throw new IllegalArgumentException("Permissão granular já existe para role/recurso/ação/escopo");
        }
        PermissaoGranular p = new PermissaoGranular();
        p.setRoleId(roleId);
        p.setRecurso(recurso);
        p.setAcao(acao);
        p.setEscopo(escopo != null ? escopo : PermissaoGranular.ESCOPO_PROPRIA);
        p.setCondicao(condicao);
        p.setDescricao(descricao);
        p.setAtivo(true);
        return repository.save(p);
    }

    @Transactional(readOnly = true)
    public List<PermissaoGranular> listarPorRole(Long roleId) {
        return repository.findByRoleId(roleId);
    }

    @Transactional
    public void desativar(Long id) {
        repository.findById(id).ifPresent(p -> {
            p.setAtivo(false);
            repository.save(p);
        });
    }

    @java.lang.SuppressWarnings("all")
    public PermissaoGranularService(final PermissaoGranularRepository repository) {
        this.repository = repository;
    }
}
