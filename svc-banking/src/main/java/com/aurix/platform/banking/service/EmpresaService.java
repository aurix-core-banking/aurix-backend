package com.aurix.platform.banking.service;

import com.aurix.platform.banking.dto.EmpresaDTO;
import com.aurix.platform.banking.entity.Empresa;
import com.aurix.platform.banking.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class EmpresaService {
    
    @Autowired
    private EmpresaRepository empresaRepository;
    
    public List<Empresa> listarTodasEmpresas() {
        return empresaRepository.findAll();
    }
    
    public Empresa buscarEmpresaPorId(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + id));
    }
    
    public Empresa buscarEmpresaPorCodigo(String codigo) {
        return empresaRepository.findByCodigoEmpresa(codigo)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com código: " + codigo));
    }
    
    public List<Empresa> listarEmpresasAtivas() {
        return empresaRepository.findEmpresasAtivas();
    }
    
    public Empresa criarEmpresa(EmpresaDTO empresaDTO) {
        if (empresaRepository.existsByCodigoEmpresa(empresaDTO.getCodigoEmpresa())) {
            throw new RuntimeException("Já existe uma empresa com o código: " + empresaDTO.getCodigoEmpresa());
        }
        
        if (empresaDTO.getCnpj() != null && empresaRepository.existsByCnpj(empresaDTO.getCnpj())) {
            throw new RuntimeException("Já existe uma empresa com o CNPJ: " + empresaDTO.getCnpj());
        }
        
        Empresa empresa = new Empresa();
        empresa.setCodigoEmpresa(empresaDTO.getCodigoEmpresa());
        empresa.setNomeEmpresa(empresaDTO.getNomeEmpresa());
        empresa.setCnpj(empresaDTO.getCnpj());
        empresa.setDescricao(empresaDTO.getDescricao());
        empresa.setStatus(empresaDTO.getStatus() != null ? empresaDTO.getStatus() : Empresa.StatusEmpresa.ATIVA);
        empresa.setDadosEmpresa(empresaDTO.getDadosEmpresa());
        empresa.setConfiguracoesEmpresa(empresaDTO.getConfiguracoesEmpresa());
        empresa.setDataCriacao(LocalDateTime.now());
        empresa.setDataAtualizacao(LocalDateTime.now());
        
        return empresaRepository.save(empresa);
    }
    
    public Empresa atualizarEmpresa(Long id, EmpresaDTO empresaDTO) {
        Empresa empresa = buscarEmpresaPorId(id);
        
        if (!empresa.getCodigoEmpresa().equals(empresaDTO.getCodigoEmpresa()) && 
            empresaRepository.existsByCodigoEmpresa(empresaDTO.getCodigoEmpresa())) {
            throw new RuntimeException("Já existe uma empresa com o código: " + empresaDTO.getCodigoEmpresa());
        }
        
        if (empresaDTO.getCnpj() != null && !empresa.getCnpj().equals(empresaDTO.getCnpj()) && 
            empresaRepository.existsByCnpj(empresaDTO.getCnpj())) {
            throw new RuntimeException("Já existe uma empresa com o CNPJ: " + empresaDTO.getCnpj());
        }
        
        empresa.setCodigoEmpresa(empresaDTO.getCodigoEmpresa());
        empresa.setNomeEmpresa(empresaDTO.getNomeEmpresa());
        empresa.setCnpj(empresaDTO.getCnpj());
        empresa.setDescricao(empresaDTO.getDescricao());
        empresa.setStatus(empresaDTO.getStatus());
        empresa.setDadosEmpresa(empresaDTO.getDadosEmpresa());
        empresa.setConfiguracoesEmpresa(empresaDTO.getConfiguracoesEmpresa());
        empresa.setDataAtualizacao(LocalDateTime.now());
        
        return empresaRepository.save(empresa);
    }
    
    public void excluirEmpresa(Long id) {
        Empresa empresa = buscarEmpresaPorId(id);
        empresa.setStatus(Empresa.StatusEmpresa.INATIVA);
        empresa.setDataAtualizacao(LocalDateTime.now());
        empresaRepository.save(empresa);
    }
}
