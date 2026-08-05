package com.aurix.platform.platform.service;

import com.aurix.platform.platform.dto.InstituicaoDTO;
import com.aurix.platform.platform.entity.Instituicao;
import com.aurix.platform.platform.repository.InstituicaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstituicaoService {
    private final InstituicaoRepository instituicaoRepository;

    @Transactional(readOnly = true)
    public List<InstituicaoDTO> listar() {
        return instituicaoRepository.findAll().stream().map(InstituicaoDTO::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InstituicaoDTO buscarPorId(Long id) {
        return instituicaoRepository.findById(id).map(InstituicaoDTO::from).orElse(null);
    }

    @Transactional(readOnly = true)
    public InstituicaoDTO buscarPorTenantId(String tenantId) {
        return instituicaoRepository.findByTenantId(tenantId).map(InstituicaoDTO::from).orElse(null);
    }

    @Transactional
    public InstituicaoDTO criar(InstituicaoDTO dto) {
        if (instituicaoRepository.existsByTenantId(dto.getTenantId())) {
            throw new IllegalArgumentException("tenant_id ja existe: " + dto.getTenantId());
        }
        if (dto.getCnpj() != null && !dto.getCnpj().isBlank() && instituicaoRepository.existsByCnpj(dto.getCnpj())) {
            throw new IllegalArgumentException("cnpj ja cadastrado: " + dto.getCnpj());
        }
        Instituicao e = toEntity(dto);
        e.setId(null);
        e.setStatus(Instituicao.StatusInstituicao.PENDENTE);
        return InstituicaoDTO.from(instituicaoRepository.save(e));
    }

    @Transactional
    public InstituicaoDTO atualizar(Long id, InstituicaoDTO dto) {
        Instituicao existing = instituicaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Instituicao nao encontrada: " + id));
        if (dto.getCnpj() != null && !dto.getCnpj().equals(existing.getCnpj()) && instituicaoRepository.existsByCnpj(dto.getCnpj())) {
            throw new IllegalArgumentException("cnpj ja cadastrado: " + dto.getCnpj());
        }
        existing.setNome(dto.getNome());
        existing.setCnpj(dto.getCnpj());
        existing.setEmailContato(dto.getEmailContato());
        existing.setTelefoneContato(dto.getTelefoneContato());
        existing.setPlano(dto.getPlano());
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
        existing.setDeploymentProfile(dto.getDeploymentProfile());
        return InstituicaoDTO.from(instituicaoRepository.save(existing));
    }

    private static Instituicao toEntity(InstituicaoDTO dto) {
        return Instituicao.builder().id(dto.getId()).tenantId(dto.getTenantId()).nome(dto.getNome()).cnpj(dto.getCnpj()).emailContato(dto.getEmailContato()).telefoneContato(dto.getTelefoneContato()).plano(dto.getPlano()).status(dto.getStatus() != null ? dto.getStatus() : Instituicao.StatusInstituicao.PENDENTE).deploymentProfile(dto.getDeploymentProfile()).build();
    }

    @java.lang.SuppressWarnings("all")
    public InstituicaoService(final InstituicaoRepository instituicaoRepository) {
        this.instituicaoRepository = instituicaoRepository;
    }
}
