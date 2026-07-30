package com.aurix.platform.finance.service;

import com.aurix.platform.finance.entity.PerfilFinanceiroCliente;
import com.aurix.platform.finance.repository.PerfilFinanceiroClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class PerfilFinanceiroClienteService {
    private final PerfilFinanceiroClienteRepository repository;

    public PerfilFinanceiroCliente criarPerfil(Long clienteId, String codigoCliente) {
        if (repository.existsByClienteId(clienteId)) {
            throw new IllegalArgumentException("Perfil financeiro já existe para cliente " + clienteId);
        }
        PerfilFinanceiroCliente perfil = new PerfilFinanceiroCliente();
        perfil.setClienteId(clienteId);
        perfil.setCodigoCliente(codigoCliente);
        return repository.save(perfil);
    }

    @Transactional(readOnly = true)
    public Optional<PerfilFinanceiroCliente> buscarPorClienteId(Long clienteId) {
        return repository.findByClienteId(clienteId);
    }

    public PerfilFinanceiroCliente atualizarLimiteCredito(Long clienteId, BigDecimal limiteCredito) {
        PerfilFinanceiroCliente perfil = repository.findByClienteId(clienteId)
            .orElseThrow(() -> new IllegalArgumentException("Perfil financeiro não encontrado para cliente " + clienteId));
        perfil.setLimiteCredito(limiteCredito);
        return repository.save(perfil);
    }

    public PerfilFinanceiroCliente atualizarScore(Long clienteId, Integer scoreCredito) {
        PerfilFinanceiroCliente perfil = repository.findByClienteId(clienteId)
            .orElseThrow(() -> new IllegalArgumentException("Perfil financeiro não encontrado para cliente " + clienteId));
        perfil.setScoreCredito(scoreCredito);
        return repository.save(perfil);
    }

    public void removerPorClienteId(Long clienteId) {
        repository.deleteByClienteId(clienteId);
    }

    public PerfilFinanceiroClienteService(PerfilFinanceiroClienteRepository repository) {
        this.repository = repository;
    }
}
