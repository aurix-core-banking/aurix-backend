package com.aurix.platform.platform.service;

import com.aurix.platform.platform.entity.MfaConfig;
import com.aurix.platform.platform.entity.MfaToken;
import com.aurix.platform.platform.repository.MfaConfigRepository;
import com.aurix.platform.platform.repository.MfaTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class MfaService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MfaService.class);
    private final MfaConfigRepository mfaConfigRepository;
    private final MfaTokenRepository mfaTokenRepository;
    private final SecureRandom random = new SecureRandom();

    public MfaConfig configurarMfa(Long usuarioId, MfaConfig.TipoMfa tipoMfa, String valorConfigurado) {
        Optional<MfaConfig> existente = mfaConfigRepository.findByUsuarioIdAndTipoMfa(usuarioId, tipoMfa);
        MfaConfig config = existente.orElse(new MfaConfig());
        config.setUsuarioId(usuarioId);
        config.setTipoMfa(tipoMfa);
        config.setValorConfigurado(valorConfigurado);
        config.setAtivo(true);
        config.setDataConfiguracao(LocalDateTime.now());
        if (tipoMfa == MfaConfig.TipoMfa.APP_AUTHENTICATOR) {
            config.setCodigoBackup(gerarCodigoBackup());
        }
        return mfaConfigRepository.save(config);
    }

    public MfaToken gerarTokenMfa(Long usuarioId, String sessaoId, MfaToken.TipoMfa tipoMfa) {
        List<MfaConfig> configsAtivos = mfaConfigRepository.findByUsuarioIdAndAtivoTrue(usuarioId);
        boolean tipoConfigurado = configsAtivos.stream().anyMatch(c -> c.getTipoMfa().name().equals(tipoMfa.name()));
        if (!tipoConfigurado && tipoMfa != MfaToken.TipoMfa.BACKUP_CODE) {
            throw new RuntimeException("Tipo de MFA não configurado para o usuário");
        }
        MfaToken token = new MfaToken();
        token.setCodigoToken("MFA-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        token.setUsuarioId(usuarioId);
        token.setSessaoId(sessaoId);
        token.setTipoMfa(tipoMfa);
        token.setStatus(MfaToken.StatusToken.PENDENTE);
        token.setDataCriacao(LocalDateTime.now());
        token.setDataExpiracao(LocalDateTime.now().plusMinutes(10));
        String codigo = gerarCodigo(tipoMfa);
        token.setCodigo(codigo);
        if (tipoMfa == MfaToken.TipoMfa.SMS || tipoMfa == MfaToken.TipoMfa.EMAIL) {
            enviarCodigo(token);
        }
        return mfaTokenRepository.save(token);
    }

    public boolean validarTokenMfa(String codigoToken, String codigoInformado) {
        Optional<MfaToken> tokenOpt = mfaTokenRepository.findTokenValido(codigoToken, LocalDateTime.now());
        if (tokenOpt.isEmpty()) {
            return false;
        }
        MfaToken token = tokenOpt.get();
        if (token.getTentativasValidacao() >= token.getMaxTentativas()) {
            token.setStatus(MfaToken.StatusToken.BLOQUEADO);
            mfaTokenRepository.save(token);
            return false;
        }
        token.setTentativasValidacao(token.getTentativasValidacao() + 1);
        boolean valido = false;
        if (token.getTipoMfa() == MfaToken.TipoMfa.APP_AUTHENTICATOR) {
            valido = validarTotp(codigoInformado, token.getCodigo());
        } else if (token.getTipoMfa() == MfaToken.TipoMfa.BACKUP_CODE) {
            valido = validarBackupCode(token.getUsuarioId(), codigoInformado);
        } else {
            valido = token.getCodigo().equals(codigoInformado);
        }
        if (valido) {
            token.setStatus(MfaToken.StatusToken.VALIDADO);
            token.setDataValidacao(LocalDateTime.now());
            mfaTokenRepository.save(token);
            return true;
        } else {
            mfaTokenRepository.save(token);
            return false;
        }
    }

    public boolean validarBiometria(Long usuarioId, String dadosBiometricos) {
        Optional<MfaConfig> config = mfaConfigRepository.findByUsuarioIdAndTipoMfa(usuarioId, MfaConfig.TipoMfa.BIOMETRIA);
        if (config.isEmpty() || !config.get().getAtivo()) {
            return false;
        }
        return validarBiometriaComDados(dadosBiometricos, config.get().getValorConfigurado());
    }

    public List<MfaConfig> listarConfiguracoesMfa(Long usuarioId) {
        return mfaConfigRepository.findByUsuarioId(usuarioId);
    }

    public void desativarMfa(Long usuarioId, MfaConfig.TipoMfa tipoMfa) {
        Optional<MfaConfig> config = mfaConfigRepository.findByUsuarioIdAndTipoMfa(usuarioId, tipoMfa);
        if (config.isPresent()) {
            config.get().setAtivo(false);
            mfaConfigRepository.save(config.get());
        }
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void limparTokensExpirados() {
        List<MfaToken> tokensExpirados = mfaTokenRepository.findAll().stream().filter(t -> t.getStatus() == MfaToken.StatusToken.PENDENTE && t.getDataExpiracao() != null && t.getDataExpiracao().isBefore(LocalDateTime.now())).toList();
        for (MfaToken token : tokensExpirados) {
            token.setStatus(MfaToken.StatusToken.EXPIRADO);
            mfaTokenRepository.save(token);
        }
        log.info("Limpeza de tokens MFA: {} tokens expirados processados", tokensExpirados.size());
    }

    private String gerarCodigo(MfaToken.TipoMfa tipoMfa) {
        if (tipoMfa == MfaToken.TipoMfa.APP_AUTHENTICATOR) {
            return gerarTotp();
        } else if (tipoMfa == MfaToken.TipoMfa.BACKUP_CODE) {
            return gerarCodigoBackup();
        } else {
            return String.format("%06d", random.nextInt(1000000));
        }
    }

    private String gerarTotp() {
        return String.format("%06d", random.nextInt(1000000));
    }

    private String gerarCodigoBackup() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
    }

    private void enviarCodigo(MfaToken token) {
        log.info("Enviando código MFA {} para {}", token.getCodigo(), token.getDestinatario());
    }

    private boolean validarTotp(String codigoInformado, String codigoEsperado) {
        return codigoInformado.equals(codigoEsperado);
    }

    private boolean validarBackupCode(Long usuarioId, String codigo) {
        Optional<MfaConfig> config = mfaConfigRepository.findByUsuarioIdAndTipoMfa(usuarioId, MfaConfig.TipoMfa.APP_AUTHENTICATOR);
        return config.isPresent() && codigo.equals(config.get().getCodigoBackup());
    }

    private boolean validarBiometriaComDados(String dadosInformados, String dadosArmazenados) {
        return dadosInformados.equals(dadosArmazenados);
    }

    @java.lang.SuppressWarnings("all")
    public MfaService(final MfaConfigRepository mfaConfigRepository, final MfaTokenRepository mfaTokenRepository) {
        this.mfaConfigRepository = mfaConfigRepository;
        this.mfaTokenRepository = mfaTokenRepository;
    }
}
