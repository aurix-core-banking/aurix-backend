package com.aurix.platform.contracts.service;

import com.aurix.platform.contracts.dto.TemplateRequest;
import com.aurix.platform.contracts.dto.TemplateResponse;
import com.aurix.platform.contracts.entity.Contrato;
import com.aurix.platform.contracts.entity.TemplateContrato;
import com.aurix.platform.contracts.exception.TemplateNaoEncontradoException;
import com.aurix.platform.contracts.repository.TemplateContratoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class TemplateContratoService {

    private final TemplateContratoRepository templateRepository;
    private final ContratoService contratoService;

    public TemplateContratoService(TemplateContratoRepository templateRepository,
                                   ContratoService contratoService) {
        this.templateRepository = templateRepository;
        this.contratoService = contratoService;
    }

    @Transactional
    public TemplateContrato criar(TemplateRequest request) {
        if (templateRepository.existsByCodigo(request.codigo())) {
            throw new IllegalArgumentException("Já existe template com o código: " + request.codigo());
        }
        TemplateContrato template = new TemplateContrato();
        template.setCodigo(request.codigo());
        template.setNome(request.nome());
        template.setTipoContrato(request.tipoContrato());
        template.setCorpoTexto(request.corpoTexto());
        template.setStatus(TemplateContrato.StatusTemplate.ATIVO);
        template.setVersao(1);
        return templateRepository.save(template);
    }

    @Transactional
    public TemplateContrato atualizar(Long id, TemplateRequest request) {
        TemplateContrato template = buscarEntidade(id);
        template.setNome(request.nome());
        template.setTipoContrato(request.tipoContrato());
        template.setCorpoTexto(request.corpoTexto());
        template.setVersao(template.getVersao() + 1);
        return templateRepository.save(template);
    }

    @Transactional
    public TemplateContrato inativar(Long id) {
        TemplateContrato template = buscarEntidade(id);
        template.setStatus(TemplateContrato.StatusTemplate.INATIVO);
        return templateRepository.save(template);
    }

    @Transactional(readOnly = true)
    public TemplateContrato buscarEntidade(Long id) {
        return templateRepository.findById(id)
            .orElseThrow(() -> new TemplateNaoEncontradoException(id));
    }

    @Transactional(readOnly = true)
    public TemplateContrato buscarPorCodigo(String codigo) {
        return templateRepository.findByCodigo(codigo)
            .orElseThrow(() -> new TemplateNaoEncontradoException(codigo));
    }

    @Transactional(readOnly = true)
    public List<TemplateContrato> listar(Contrato.TipoContrato tipo, TemplateContrato.StatusTemplate status) {
        if (tipo != null) {
            return templateRepository.findByTipoContrato(tipo);
        }
        if (status != null) {
            return templateRepository.findByStatus(status);
        }
        return templateRepository.findAll();
    }

    @Transactional(readOnly = true)
    public String gerarDocumento(Long templateId, Long contratoId) {
        TemplateContrato template = buscarEntidade(templateId);
        Contrato contrato = contratoService.buscarEntidade(contratoId);
        Map<String, String> dados = Map.of(
            "numero", String.valueOf(contrato.getNumeroContrato()),
            "clienteId", String.valueOf(contrato.getClienteId()),
            "clienteDocumento", String.valueOf(contrato.getClienteDocumento()),
            "tipoContrato", contrato.getTipoContrato().name(),
            "valor", String.valueOf(contrato.getValor()),
            "prazoMeses", String.valueOf(contrato.getPrazoMeses()),
            "valorParcela", String.valueOf(contrato.getValorParcela()),
            "taxaJuros", String.valueOf(contrato.getTaxaJuros())
        );
        String corpo = template.getCorpoTexto();
        for (Map.Entry<String, String> entry : dados.entrySet()) {
            corpo = corpo.replace("{{" + entry.getKey() + "}}", entry.getValue() == null ? "" : entry.getValue());
        }
        return corpo;
    }
}
