package com.aurix.platform.payments.pix.service;

import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.shared.repository.PixChaveRepository;
import com.aurix.platform.shared.dto.PixChaveDTO;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.PixChave;
import com.aurix.platform.shared.util.CPFUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gestão de chaves PIX
 */
@Service
@Transactional
public class PixChaveService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PixChaveService.class);
    private final PixChaveRepository pixChaveRepository;
    private final ContaRepository contaRepository;

    /**
     * Cadastra uma nova chave PIX
     */
    public PixChaveDTO cadastrarChavePix(PixChaveDTO pixChaveDTO) {
        log.info("Cadastrando chave PIX: {}", pixChaveDTO.getChavePix());
        // Validar chave PIX
        validarChavePix(pixChaveDTO.getChavePix(), pixChaveDTO.getTipoChave());
        // Verificar se já existe
        if (pixChaveRepository.existsByChavePix(pixChaveDTO.getChavePix())) {
            throw new IllegalArgumentException("Chave PIX " + pixChaveDTO.getChavePix() + " já está cadastrada");
        }
        // Buscar conta
        Conta conta = contaRepository.findById(pixChaveDTO.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada: " + pixChaveDTO.getContaId()));
        // Criar entidade
        PixChave pixChave = new PixChave();
        pixChave.setConta(conta);
        pixChave.setChavePix(pixChaveDTO.getChavePix());
        pixChave.setTipoChave(pixChaveDTO.getTipoChave());
        pixChave.setNomeTitular(pixChaveDTO.getNomeTitular());
        pixChave.setStatus(PixChave.StatusChavePix.ATIVA);
        pixChave.setDadosAdicionais(pixChaveDTO.getDadosAdicionais());
        // Salvar
        PixChave chaveSalva = pixChaveRepository.save(pixChave);
        log.info("Chave PIX cadastrada com ID: {}", chaveSalva.getId());
        return converterParaDTO(chaveSalva);
    }

    /**
     * Busca chave PIX por ID
     */
    @Transactional(readOnly = true)
    public PixChaveDTO buscarChavePixPorId(Long id) {
        log.info("Buscando chave PIX por ID: {}", id);
        PixChave pixChave = pixChaveRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Chave PIX não encontrada"));
        return converterParaDTO(pixChave);
    }

    /**
     * Busca chave PIX por chave
     */
    @Transactional(readOnly = true)
    public PixChaveDTO buscarChavePixPorChave(String chavePix) {
        log.info("Buscando chave PIX: {}", chavePix);
        PixChave pixChave = pixChaveRepository.findByChavePix(chavePix).orElseThrow(() -> new IllegalArgumentException("Chave PIX não encontrada"));
        return converterParaDTO(pixChave);
    }

    /**
     * Lista chaves por conta
     */
    @Transactional(readOnly = true)
    public List<PixChaveDTO> listarChavesPorConta(Long contaId) {
        log.info("Listando chaves PIX da conta ID: {}", contaId);
        return pixChaveRepository.findByContaId(contaId).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista chaves ativas por conta
     */
    @Transactional(readOnly = true)
    public List<PixChaveDTO> listarChavesAtivasPorConta(Long contaId) {
        log.info("Listando chaves PIX ativas da conta ID: {}", contaId);
        return pixChaveRepository.findChavesAtivasByContaId(contaId).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Inativa chave PIX
     */
    public void inativarChavePix(Long id) {
        log.info("Inativando chave PIX ID: {}", id);
        PixChave pixChave = pixChaveRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Chave PIX não encontrada"));
        pixChave.setStatus(PixChave.StatusChavePix.INATIVA);
        pixChave.setDataInativacao(java.time.LocalDateTime.now());
        pixChaveRepository.save(pixChave);
        log.info("Chave PIX inativada com sucesso");
    }

    /**
     * Ativa chave PIX
     */
    public void ativarChavePix(Long id) {
        log.info("Ativando chave PIX ID: {}", id);
        PixChave pixChave = pixChaveRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Chave PIX não encontrada"));
        pixChave.setStatus(PixChave.StatusChavePix.ATIVA);
        pixChave.setDataInativacao(null);
        pixChaveRepository.save(pixChave);
        log.info("Chave PIX ativada com sucesso");
    }

    /**
     * Valida chave PIX
     */
    private void validarChavePix(String chavePix, PixChave.TipoChavePix tipoChave) {
        switch (tipoChave) {
        case CPF: 
            if (!CPFUtil.isValid(chavePix)) {
                throw new IllegalArgumentException("CPF inválido: " + chavePix);
            }
            break;
        case EMAIL: 
            if (!chavePix.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw new IllegalArgumentException("Email inválido: " + chavePix);
            }
            break;
        case TELEFONE: 
            if (!chavePix.matches("^\\+?[1-9]\\d{1,14}$")) {
                throw new IllegalArgumentException("Telefone inválido: " + chavePix);
            }
            break;
        case CHAVE_ALEATORIA: 
            if (chavePix.length() != 36) {
                throw new IllegalArgumentException("Chave aleatória deve ter 36 caracteres");
            }
            break;
        default: 
            break;
        }
    }

    /**
     * Converte entidade para DTO
     */
    private PixChaveDTO converterParaDTO(PixChave pixChave) {
        PixChaveDTO dto = new PixChaveDTO();
        dto.setId(pixChave.getId());
        dto.setChavePix(pixChave.getChavePix());
        dto.setContaId(pixChave.getConta() != null ? pixChave.getConta().getId() : null);
        dto.setContaNumero(pixChave.getConta() != null ? pixChave.getConta().getNumeroConta() : null);
        dto.setTipoChave(pixChave.getTipoChave());
        dto.setNomeTitular(pixChave.getNomeTitular());
        dto.setStatus(pixChave.getStatus());
        dto.setDataCadastro(pixChave.getDataCadastro());
        dto.setDataInativacao(pixChave.getDataInativacao());
        dto.setDadosAdicionais(pixChave.getDadosAdicionais());
        dto.setDataCriacao(pixChave.getDataCriacao() != null ? pixChave.getDataCriacao().toString() : null);
        dto.setDataAtualizacao(pixChave.getDataAtualizacao() != null ? pixChave.getDataAtualizacao().toString() : null);
        return dto;
    }

    @java.lang.SuppressWarnings("all")
    public PixChaveService(final PixChaveRepository pixChaveRepository, final ContaRepository contaRepository) {
        this.pixChaveRepository = pixChaveRepository;
        this.contaRepository = contaRepository;
    }
}
