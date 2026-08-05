package com.aurix.platform.platform.service;

import com.aurix.platform.platform.entity.SessaoInternetBanking;
import com.aurix.platform.platform.entity.TransacaoInternetBanking;
import com.aurix.platform.platform.entity.LogAtividadeInternetBanking;
import com.aurix.platform.platform.repository.SessaoInternetBankingRepository;
import com.aurix.platform.platform.repository.TransacaoInternetBankingRepository;
import com.aurix.platform.platform.repository.LogAtividadeInternetBankingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Service principal para o Internet Banking
 * 
 * Gerencia funcionalidades do canal web para clientes
 */
@Service
@Transactional
@SuppressWarnings({"PMD.UnusedFormalParameter"})
public class InternetBankingService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InternetBankingService.class);
    private final SessaoInternetBankingRepository sessaoRepository;
    private final TransacaoInternetBankingRepository transacaoRepository;
    private final LogAtividadeInternetBankingRepository logRepository;

    // ========== GESTÃO DE SESSÕES ==========
    /**
     * Cria nova sessão de Internet Banking
     */
    public SessaoInternetBanking criarSessao(String clienteId, String usuarioId, String ipAddress, String userAgent, String deviceId) {
        log.info("Criando nova sessão de Internet Banking: Cliente={}, Usuario={}, IP={}", clienteId, usuarioId, ipAddress);
        try {
            String sessaoId = UUID.randomUUID().toString();
            LocalDateTime agora = LocalDateTime.now();
            SessaoInternetBanking sessao = SessaoInternetBanking.builder().sessaoId(sessaoId).clienteId(clienteId).usuarioId(usuarioId).ipAddress(ipAddress).userAgent(userAgent).deviceId(deviceId).deviceType(determinarTipoDispositivo(userAgent)).browser(extrairBrowser(userAgent)).operatingSystem(extrairSistemaOperacional(userAgent)).status(SessaoInternetBanking.StatusSessao.ATIVA).dataLogin(agora).dataExpiracao(agora.plusMinutes(30)).ultimaAtividade(agora).timeoutMinutos(30).mfaVerificado(false).biometricoVerificado(false).tentativasFalha(0).bloqueado(false).build();
            SessaoInternetBanking sessaoSalva = sessaoRepository.save(sessao);
            // Log da atividade
            logarAtividade(sessaoId, clienteId, usuarioId, LogAtividadeInternetBanking.TipoAtividade.LOGIN, LogAtividadeInternetBanking.CategoriaAtividade.AUTENTICACAO, "LOGIN", "Login realizado com sucesso", "SUCCESS", ipAddress, userAgent);
            log.info("Sessão criada com sucesso: {}", sessaoId);
            return sessaoSalva;
        } catch (Exception e) {
            log.error("Erro ao criar sessão de Internet Banking: {}", e.getMessage());
            throw new RuntimeException("Erro ao criar sessão", e);
        }
    }

    /**
     * Valida e atualiza sessão
     */
    public boolean validarSessao(String sessaoId) {
        log.debug("Validando sessão: {}", sessaoId);
        try {
            Optional<SessaoInternetBanking> sessaoOpt = sessaoRepository.findBySessaoId(sessaoId);
            if (sessaoOpt.isEmpty()) {
                log.warn("Sessão não encontrada: {}", sessaoId);
                return false;
            }
            SessaoInternetBanking sessao = sessaoOpt.get();
            // Verificar se sessão está ativa
            if (sessao.getStatus() != SessaoInternetBanking.StatusSessao.ATIVA) {
                log.warn("Sessão não está ativa: {} - Status: {}", sessaoId, sessao.getStatus());
                return false;
            }
            // Verificar se não expirou
            if (sessao.getDataExpiracao().isBefore(LocalDateTime.now())) {
                log.warn("Sessão expirada: {}", sessaoId);
                sessao.setStatus(SessaoInternetBanking.StatusSessao.EXPIRADA);
                sessaoRepository.save(sessao);
                return false;
            }
            // Atualizar última atividade
            sessao.setUltimaAtividade(LocalDateTime.now());
            sessao.setDataExpiracao(LocalDateTime.now().plusMinutes(sessao.getTimeoutMinutos()));
            sessaoRepository.save(sessao);
            return true;
        } catch (Exception e) {
            log.error("Erro ao validar sessão {}: {}", sessaoId, e.getMessage());
            return false;
        }
    }

    /**
     * Encerra sessão
     */
    public void encerrarSessao(String sessaoId) {
        log.info("Encerrando sessão: {}", sessaoId);
        try {
            Optional<SessaoInternetBanking> sessaoOpt = sessaoRepository.findBySessaoId(sessaoId);
            if (sessaoOpt.isPresent()) {
                SessaoInternetBanking sessao = sessaoOpt.get();
                sessao.setStatus(SessaoInternetBanking.StatusSessao.ENCERRADA);
                sessao.setDataLogout(LocalDateTime.now());
                sessaoRepository.save(sessao);
                // Log da atividade
                logarAtividade(sessaoId, sessao.getClienteId(), sessao.getUsuarioId(), LogAtividadeInternetBanking.TipoAtividade.LOGOUT, LogAtividadeInternetBanking.CategoriaAtividade.AUTENTICACAO, "LOGOUT", "Logout realizado", "SUCCESS", sessao.getIpAddress(), sessao.getUserAgent());
                log.info("Sessão encerrada com sucesso: {}", sessaoId);
            }
        } catch (Exception e) {
            log.error("Erro ao encerrar sessão {}: {}", sessaoId, e.getMessage());
        }
    }

    // ========== GESTÃO DE TRANSAÇÕES ==========
    /**
     * Processa transação via Internet Banking
     */
    public TransacaoInternetBanking processarTransacao(String sessaoId, String clienteId, String contaOrigem, String contaDestino, TransacaoInternetBanking.TipoTransacao tipoTransacao, BigDecimal valor, String descricao) {
        log.info("Processando transação via Internet Banking: Sessão={}, Cliente={}, Tipo={}, Valor={}", sessaoId, clienteId, tipoTransacao, valor);
        try {
            // Validar sessão
            if (!validarSessao(sessaoId)) {
                throw new RuntimeException("Sessão inválida ou expirada");
            }
            // Obter dados da sessão
            Optional<SessaoInternetBanking> sessaoOpt = sessaoRepository.findBySessaoId(sessaoId);
            if (sessaoOpt.isEmpty()) {
                throw new RuntimeException("Sessão não encontrada");
            }
            SessaoInternetBanking sessao = sessaoOpt.get();
            // Criar transação
            String transacaoId = UUID.randomUUID().toString();
            LocalDateTime agora = LocalDateTime.now();
            TransacaoInternetBanking transacao = TransacaoInternetBanking.builder().transacaoId(transacaoId).sessaoId(sessaoId).clienteId(clienteId).contaOrigem(contaOrigem).contaDestino(contaDestino).tipoTransacao(tipoTransacao).valor(valor).taxa(calcularTaxa(tipoTransacao, valor)).valorTotal(valor.add(calcularTaxa(tipoTransacao, valor))).descricao(descricao).status(TransacaoInternetBanking.StatusTransacao.PENDENTE).dataTransacao(agora).ipAddress(sessao.getIpAddress()).userAgent(sessao.getUserAgent()).deviceFingerprint(sessao.getDeviceId()).mfaVerificado(sessao.getMfaVerificado()).biometricoVerificado(sessao.getBiometricoVerificado()).riscoScore(calcularRiscoScore(valor, tipoTransacao, sessao)).fraudeDetectada(false).build();
            TransacaoInternetBanking transacaoSalva = transacaoRepository.save(transacao);
            // Log da atividade
            logarAtividade(sessaoId, clienteId, sessao.getUsuarioId(), LogAtividadeInternetBanking.TipoAtividade.TRANSACAO, LogAtividadeInternetBanking.CategoriaAtividade.TRANSACOES, "TRANSACAO", "Transação iniciada: " + tipoTransacao, "SUCCESS", sessao.getIpAddress(), sessao.getUserAgent(), valor, contaOrigem, transacaoId);
            log.info("Transação processada com sucesso: {}", transacaoId);
            return transacaoSalva;
        } catch (Exception e) {
            log.error("Erro ao processar transação via Internet Banking: {}", e.getMessage());
            // Log de erro
            logarAtividade(sessaoId, clienteId, "", LogAtividadeInternetBanking.TipoAtividade.ERRO, LogAtividadeInternetBanking.CategoriaAtividade.TRANSACOES, "TRANSACAO", "Erro ao processar transação: " + e.getMessage(), "ERROR", "", "", valor, contaOrigem, "");
            throw new RuntimeException("Erro ao processar transação", e);
        }
    }

    // ========== LOG DE ATIVIDADES ==========
    /**
     * Registra atividade no log
     */
    public void logarAtividade(String sessaoId, String clienteId, String usuarioId, LogAtividadeInternetBanking.TipoAtividade tipoAtividade, LogAtividadeInternetBanking.CategoriaAtividade categoria, String acao, String descricao, String resultado, String ipAddress, String userAgent) {
        logarAtividade(sessaoId, clienteId, usuarioId, tipoAtividade, categoria, acao, descricao, resultado, ipAddress, userAgent, null, null, null);
    }

    /**
     * Registra atividade no log (completa)
     */
    public void logarAtividade(String sessaoId, String clienteId, String usuarioId, LogAtividadeInternetBanking.TipoAtividade tipoAtividade, LogAtividadeInternetBanking.CategoriaAtividade categoria, String acao, String descricao, String resultado, String ipAddress, String userAgent, BigDecimal valor, String contaEnvolvida, String transacaoId) {
        try {
            String logId = UUID.randomUUID().toString();
            LogAtividadeInternetBanking log = LogAtividadeInternetBanking.builder().logId(logId).sessaoId(sessaoId).clienteId(clienteId).usuarioId(usuarioId).tipoAtividade(tipoAtividade).categoria(categoria).acao(acao).descricao(descricao).resultado(resultado).status("SUCCESS".equals(resultado) ? "SUCCESS" : "ERROR").ipAddress(ipAddress).userAgent(userAgent).valor(valor).contaEnvolvida(contaEnvolvida).transacaoId(transacaoId).dataAtividade(LocalDateTime.now()).build();
            logRepository.save(log);
        } catch (Exception e) {
            log.error("Erro ao registrar log de atividade: {}", e.getMessage());
        }
    }

    // ========== MÉTODOS AUXILIARES ==========
    /**
     * Calcula taxa para transação
     */
    private BigDecimal calcularTaxa(TransacaoInternetBanking.TipoTransacao tipoTransacao, BigDecimal valor) {
        switch (tipoTransacao) {
        case TRANSFERENCIA_TED: 
            return BigDecimal.valueOf(8.5); // R$ 8,50
        case TRANSFERENCIA_DOC: 
            return BigDecimal.valueOf(15.0); // R$ 15,00
        case TRANSFERENCIA_PIX: 
            return BigDecimal.ZERO; // PIX é gratuito
        case PAGAMENTO_BOLETO: 
            return BigDecimal.valueOf(2.5); // R$ 2,50
        case PAGAMENTO_CONTA: 
            return BigDecimal.valueOf(1.5); // R$ 1,50
        default: 
            return BigDecimal.ZERO;
        }
    }

    /**
     * Calcula score de risco
     */
    private Double calcularRiscoScore(BigDecimal valor, TransacaoInternetBanking.TipoTransacao tipoTransacao, SessaoInternetBanking sessao) {
        double score = 0.0;
        // Score baseado no valor
        if (valor.compareTo(BigDecimal.valueOf(10000)) > 0) {
            score += 30;
        } else if (valor.compareTo(BigDecimal.valueOf(5000)) > 0) {
            score += 20;
        } else if (valor.compareTo(BigDecimal.valueOf(1000)) > 0) {
            score += 10;
        }
        // Score baseado no tipo de transação
        switch (tipoTransacao) {
        case TRANSFERENCIA_TED: 
        case TRANSFERENCIA_DOC: 
            score += 15;
            break;
        case TRANSFERENCIA_PIX: 
            score += 5;
            break;
        case PAGAMENTO_BOLETO: 
            score += 10;
            break;
        }
        // Score baseado na sessão
        if (sessao.getTentativasFalha() > 0) {
            score += sessao.getTentativasFalha() * 10;
        }
        if (!sessao.getMfaVerificado()) {
            score += 20;
        }
        return Math.min(score, 100.0); // Máximo 100
    }

    /**
     * Determina tipo de dispositivo
     */
    private String determinarTipoDispositivo(String userAgent) {
        if (userAgent == null) return "UNKNOWN";
        userAgent = userAgent.toLowerCase();
        if (userAgent.contains("mobile") || userAgent.contains("android") || userAgent.contains("iphone")) {
            return "MOBILE";
        } else if (userAgent.contains("tablet") || userAgent.contains("ipad")) {
            return "TABLET";
        } else {
            return "DESKTOP";
        }
    }

    /**
     * Extrai browser do user agent
     */
    private String extrairBrowser(String userAgent) {
        if (userAgent == null) return "UNKNOWN";
        userAgent = userAgent.toLowerCase();
        if (userAgent.contains("chrome")) return "Chrome";
        if (userAgent.contains("firefox")) return "Firefox";
        if (userAgent.contains("safari")) return "Safari";
        if (userAgent.contains("edge")) return "Edge";
        if (userAgent.contains("opera")) return "Opera";
        return "UNKNOWN";
    }

    /**
     * Extrai sistema operacional do user agent
     */
    private String extrairSistemaOperacional(String userAgent) {
        if (userAgent == null) return "UNKNOWN";
        userAgent = userAgent.toLowerCase();
        if (userAgent.contains("windows")) return "Windows";
        if (userAgent.contains("mac")) return "macOS";
        if (userAgent.contains("linux")) return "Linux";
        if (userAgent.contains("android")) return "Android";
        if (userAgent.contains("ios")) return "iOS";
        return "UNKNOWN";
    }

    @java.lang.SuppressWarnings("all")
    public InternetBankingService(final SessaoInternetBankingRepository sessaoRepository, final TransacaoInternetBankingRepository transacaoRepository, final LogAtividadeInternetBankingRepository logRepository) {
        this.sessaoRepository = sessaoRepository;
        this.transacaoRepository = transacaoRepository;
        this.logRepository = logRepository;
    }
}
