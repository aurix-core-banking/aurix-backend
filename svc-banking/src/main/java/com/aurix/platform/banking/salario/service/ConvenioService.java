package com.aurix.platform.banking.salario.service;

import com.aurix.platform.banking.salario.dto.ConvenioRequest;
import com.aurix.platform.banking.salario.dto.ConvenioResponse;
import com.aurix.platform.banking.salario.entity.ConvenioEmpresa;
import com.aurix.platform.banking.salario.repository.ConvenioEmpresaRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConvenioService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ConvenioService.class);
    private final ConvenioEmpresaRepository repository;

    public ConvenioService(ConvenioEmpresaRepository repository) {
        this.repository = repository;
    }

    public ConvenioResponse cadastrar(ConvenioRequest request) {
        log.info("Cadastrando empresa conveniada CNPJ: {}", request.getCnpj());
        ConvenioEmpresa empresa = new ConvenioEmpresa(request.getCnpj(), request.getRazaoSocial(), request.getContaCorrenteId());
        empresa.setTenantId(TenantContext.getTenantId());
        ConvenioEmpresa salva = repository.save(empresa);
        return converterParaResponse(salva);
    }

    @Transactional(readOnly = true)
    public ConvenioResponse buscarPorId(Long id) {
        ConvenioEmpresa empresa = repository.findByTenantIdAndId(TenantContext.getTenantId(), id)
            .orElseThrow(() -> new IllegalArgumentException("Convenio nao encontrado: " + id));
        return converterParaResponse(empresa);
    }

    @Transactional(readOnly = true)
    public List<ConvenioResponse> listarAtivos() {
        return repository.findByTenantId(TenantContext.getTenantId()).stream()
            .filter(ConvenioEmpresa::getAtivo)
            .map(this::converterParaResponse)
            .collect(Collectors.toList());
    }

    public ConvenioResponse atualizar(Long id, ConvenioRequest request) {
        ConvenioEmpresa empresa = repository.findByTenantIdAndId(TenantContext.getTenantId(), id)
            .orElseThrow(() -> new IllegalArgumentException("Convenio nao encontrado: " + id));
        empresa.setCnpj(request.getCnpj());
        empresa.setRazaoSocial(request.getRazaoSocial());
        empresa.setContaCorrenteId(request.getContaCorrenteId());
        return converterParaResponse(repository.save(empresa));
    }

    private ConvenioResponse converterParaResponse(ConvenioEmpresa empresa) {
        ConvenioResponse resp = new ConvenioResponse();
        resp.setId(empresa.getId());
        resp.setCnpj(empresa.getCnpj());
        resp.setRazaoSocial(empresa.getRazaoSocial());
        resp.setContaCorrenteId(empresa.getContaCorrenteId());
        resp.setAtivo(empresa.getAtivo());
        resp.setDataCriacao(empresa.getDataCriacao());
        resp.setDataAtualizacao(empresa.getDataAtualizacao());
        return resp;
    }
}
