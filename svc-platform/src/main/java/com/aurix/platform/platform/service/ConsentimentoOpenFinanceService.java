package com.aurix.platform.platform.service;

import com.aurix.platform.platform.dto.ConsentimentoOpenFinanceDTO;
import com.aurix.platform.platform.entity.ConsentimentoOpenFinance;
import com.aurix.platform.platform.repository.ConsentimentoOpenFinanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service para gestão de consentimentos do Open Finance
 */
@Service
@Transactional
public class ConsentimentoOpenFinanceService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ConsentimentoOpenFinanceService.class);
    private final ConsentimentoOpenFinanceRepository consentimentoRepository;
    private final TokenOpenFinanceService tokenService;
    private final LogAcessoOpenFinanceService logService;

    /**
     * Cria um novo consentimento
     */
    public ConsentimentoOpenFinanceDTO criarConsentimento(ConsentimentoOpenFinanceDTO consentimentoDTO) {
        log.info("Criando novo consentimento para cliente: {}", consentimentoDTO.getClientId());
        // Gerar ID único do consentimento
        String consentId = gerarConsentId();
        consentimentoDTO.setConsentId(consentId);
        // Definir status inicial
        consentimentoDTO.setStatus(ConsentimentoOpenFinance.StatusConsentimento.PENDENTE_APROVACAO.name());
        // Validar data de expiração
        validarDataExpiracao(consentimentoDTO.getDataExpiracao());
        // Calcular score de risco
        Double riskScore = calcularRiskScore(consentimentoDTO);
        consentimentoDTO.setRiskScore(riskScore);
        consentimentoDTO.setRiskLevel(determinarRiskLevel(riskScore));
        // Salvar consentimento
        ConsentimentoOpenFinance consentimento = consentimentoDTO.toEntity();
        if (consentimento.getUserId() == null) {
            consentimento.setUserId(1L);
        }
        consentimento = consentimentoRepository.save(consentimento);
        log.info("Consentimento criado com sucesso: {}", consentId);
        return ConsentimentoOpenFinanceDTO.fromEntity(consentimento);
    }

    /**
     * Aprova um consentimento
     */
    public ConsentimentoOpenFinanceDTO aprovarConsentimento(String consentId, Long userId) {
        log.info("Aprovando consentimento: {} pelo usuário: {}", consentId, userId);
        ConsentimentoOpenFinance consentimento = buscarConsentimentoPorId(consentId);
        if (!consentimento.getStatus().equals(ConsentimentoOpenFinance.StatusConsentimento.PENDENTE_APROVACAO)) {
            throw new RuntimeException("Consentimento não está pendente de aprovação");
        }
        if (consentimento.getDataExpiracao().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Consentimento expirado");
        }
        // Atualizar status
        consentimento.setStatus(ConsentimentoOpenFinance.StatusConsentimento.APROVADO);
        consentimento.setDataAprovacao(LocalDateTime.now());
        consentimento = consentimentoRepository.save(consentimento);
        // Gerar token de acesso
        tokenService.gerarTokenParaConsentimento(consentimento);
        log.info("Consentimento aprovado com sucesso: {}", consentId);
        return ConsentimentoOpenFinanceDTO.fromEntity(consentimento);
    }

    /**
     * Rejeita um consentimento
     */
    public ConsentimentoOpenFinanceDTO rejeitarConsentimento(String consentId, String motivo) {
        log.info("Rejeitando consentimento: {} - Motivo: {}", consentId, motivo);
        ConsentimentoOpenFinance consentimento = buscarConsentimentoPorId(consentId);
        if (!consentimento.getStatus().equals(ConsentimentoOpenFinance.StatusConsentimento.PENDENTE_APROVACAO)) {
            throw new RuntimeException("Consentimento não está pendente de aprovação");
        }
        // Atualizar status
        consentimento.setStatus(ConsentimentoOpenFinance.StatusConsentimento.REJEITADO);
        consentimento.setDataRejeicao(LocalDateTime.now());
        consentimento.setMotivoRejeicao(motivo);
        consentimento = consentimentoRepository.save(consentimento);
        log.info("Consentimento rejeitado com sucesso: {}", consentId);
        return ConsentimentoOpenFinanceDTO.fromEntity(consentimento);
    }

    /**
     * Revoga um consentimento
     */
    public ConsentimentoOpenFinanceDTO revogarConsentimento(String consentId, String motivo) {
        log.info("Revogando consentimento: {} - Motivo: {}", consentId, motivo);
        ConsentimentoOpenFinance consentimento = buscarConsentimentoPorId(consentId);
        if (!consentimento.getStatus().equals(ConsentimentoOpenFinance.StatusConsentimento.APROVADO)) {
            throw new RuntimeException("Apenas consentimentos aprovados podem ser revogados");
        }
        // Atualizar status
        consentimento.setStatus(ConsentimentoOpenFinance.StatusConsentimento.REVOGADO);
        consentimento.setDataRevocacao(LocalDateTime.now());
        consentimento.setMotivoRevocacao(motivo);
        consentimento = consentimentoRepository.save(consentimento);
        // Revogar tokens associados
        tokenService.revogarTokensPorConsentimento(consentId);
        log.info("Consentimento revogado com sucesso: {}", consentId);
        return ConsentimentoOpenFinanceDTO.fromEntity(consentimento);
    }

    /**
     * Busca consentimento por ID
     */
    @Transactional(readOnly = true)
    public ConsentimentoOpenFinanceDTO buscarConsentimento(String consentId) {
        ConsentimentoOpenFinance consentimento = buscarConsentimentoPorId(consentId);
        return ConsentimentoOpenFinanceDTO.fromEntity(consentimento);
    }

    /**
     * Lista consentimentos por usuário
     */
    @Transactional(readOnly = true)
    public List<ConsentimentoOpenFinanceDTO> listarConsentimentosPorUsuario(Long userId) {
        List<ConsentimentoOpenFinance> consentimentos = consentimentoRepository.findByUserId(userId);
        return consentimentos.stream().map(ConsentimentoOpenFinanceDTO::fromEntity).collect(Collectors.toList());
    }

    /**
     * Lista consentimentos por cliente
     */
    @Transactional(readOnly = true)
    public List<ConsentimentoOpenFinanceDTO> listarConsentimentosPorCliente(String clientId) {
        List<ConsentimentoOpenFinance> consentimentos = consentimentoRepository.findByClientId(clientId);
        return consentimentos.stream().map(ConsentimentoOpenFinanceDTO::fromEntity).collect(Collectors.toList());
    }

    /**
     * Lista consentimentos ativos por usuário
     */
    @Transactional(readOnly = true)
    public List<ConsentimentoOpenFinanceDTO> listarConsentimentosAtivosPorUsuario(Long userId) {
        List<ConsentimentoOpenFinance> consentimentos = consentimentoRepository.findConsentimentosAtivosPorUsuario(userId, LocalDateTime.now());
        return consentimentos.stream().map(ConsentimentoOpenFinanceDTO::fromEntity).collect(Collectors.toList());
    }

    /**
     * Processa consentimentos expirados
     */
    public void processarConsentimentosExpirados() {
        log.info("Processando consentimentos expirados");
        List<ConsentimentoOpenFinance> expirados = consentimentoRepository.findConsentimentosExpirados(LocalDateTime.now());
        for (ConsentimentoOpenFinance consentimento : expirados) {
            consentimento.setStatus(ConsentimentoOpenFinance.StatusConsentimento.EXPIRADO);
            consentimentoRepository.save(consentimento);
            // Revogar tokens associados
            tokenService.revogarTokensPorConsentimento(consentimento.getConsentId());
            log.info("Consentimento expirado processado: {}", consentimento.getConsentId());
        }
    }

    /**
     * Verifica se usuário tem consentimento ativo para cliente
     */
    @Transactional(readOnly = true)
    public boolean verificarConsentimentoAtivo(String clientId, Long userId) {
        return consentimentoRepository.existsConsentimentoAtivo(clientId, userId, LocalDateTime.now());
    }

    /**
     * Busca consentimento por ID (método interno)
     */
    private ConsentimentoOpenFinance buscarConsentimentoPorId(String consentId) {
        return consentimentoRepository.findByConsentId(consentId).orElseThrow(() -> new RuntimeException("Consentimento não encontrado: " + consentId));
    }

    /**
     * Gera ID único para consentimento
     */
    private String gerarConsentId() {
        return "CONSENT-" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * Valida data de expiração
     */
    private void validarDataExpiracao(LocalDateTime dataExpiracao) {
        if (dataExpiracao.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Data de expiração deve ser futura");
        }
        // Máximo de 7 dias
        if (dataExpiracao.isAfter(LocalDateTime.now().plusDays(7))) {
            throw new RuntimeException("Data de expiração não pode ser superior a 7 dias");
        }
    }

    /**
     * Calcula score de risco do consentimento
     */
    private Double calcularRiskScore(ConsentimentoOpenFinanceDTO consentimento) {
        double score = 0.0;
        // Fatores de risco
        if (consentimento.getIpAddress() != null) {
            // Verificar se IP é suspeito (implementar lógica de verificação)
            score += 0.1;
        }
        if (consentimento.getDeviceId() != null) {
            // Verificar se device é conhecido (implementar lógica de verificação)
            score += 0.1;
        }
        if (consentimento.getGeolocation() != null) {
            // Verificar se localização é suspeita (implementar lógica de verificação)
            score += 0.1;
        }
        // Verificar permissões sensíveis
        if (consentimento.getPermissoes() != null) {
            for (String permissao : consentimento.getPermissoes()) {
                if (permissao.contains("SENSIVEL") || permissao.contains("FINANCEIRO")) {
                    score += 0.2;
                }
            }
        }
        return Math.min(score, 1.0);
    }

    /**
     * Determina nível de risco baseado no score
     */
    private String determinarRiskLevel(Double riskScore) {
        if (riskScore < 0.3) {
            return "BAIXO";
        } else if (riskScore < 0.7) {
            return "MEDIO";
        } else {
            return "ALTO";
        }
    }

    @java.lang.SuppressWarnings("all")
    public ConsentimentoOpenFinanceService(final ConsentimentoOpenFinanceRepository consentimentoRepository, final TokenOpenFinanceService tokenService, final LogAcessoOpenFinanceService logService) {
        this.consentimentoRepository = consentimentoRepository;
        this.tokenService = tokenService;
        this.logService = logService;
    }
}
