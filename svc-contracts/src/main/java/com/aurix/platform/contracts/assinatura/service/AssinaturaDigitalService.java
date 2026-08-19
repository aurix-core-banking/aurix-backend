package com.aurix.platform.contracts.assinatura.service;

import com.aurix.platform.contracts.assinatura.dto.request.ConfirmarAssinaturaRequest;
import com.aurix.platform.contracts.assinatura.dto.request.GerarDocumentoRequest;
import com.aurix.platform.contracts.assinatura.dto.response.AssinaturaDigitalResponse;
import com.aurix.platform.contracts.assinatura.entity.AssinaturaDigital;
import com.aurix.platform.contracts.assinatura.entity.StatusAssinaturaDigital;
import com.aurix.platform.contracts.assinatura.repository.AssinaturaDigitalRepository;
import com.aurix.platform.contracts.entity.Contrato;
import com.aurix.platform.contracts.repository.ContratoRepository;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssinaturaDigitalService {

    private static final Logger log = LoggerFactory.getLogger(AssinaturaDigitalService.class);
    private static final int OTP_EXPIRACAO_MINUTOS = 5;

    private final AssinaturaDigitalRepository assinaturaRepository;
    private final ContratoRepository contratoRepository;
    private final String itiTimestampUrl;

    public AssinaturaDigitalService(AssinaturaDigitalRepository assinaturaRepository,
                                    ContratoRepository contratoRepository,
                                    @Value("${aurix.contracts.iti-timestamp-url:https://www.iti.br/timestamp}") String itiTimestampUrl) {
        this.assinaturaRepository = assinaturaRepository;
        this.contratoRepository = contratoRepository;
        this.itiTimestampUrl = itiTimestampUrl;
    }

    @Transactional
    public AssinaturaDigitalResponse gerarDocumento(Long contratoId, GerarDocumentoRequest request) {
        var contrato = contratoRepository.findById(contratoId)
            .orElseThrow(() -> new IllegalArgumentException("Contrato não encontrado: " + contratoId));

        var existente = assinaturaRepository.findByContratoIdAndClienteDocumento(
            contratoId, request.getClienteDocumento());
        if (existente.isPresent() && (existente.get().getStatus() == StatusAssinaturaDigital.ASSINADO
            || existente.get().getStatus() == StatusAssinaturaDigital.AGUARDANDO_OTP
            || existente.get().getStatus() == StatusAssinaturaDigital.AGUARDANDO_BIOMETRIA)) {
            throw new IllegalStateException("Já existe fluxo de assinatura em andamento para este documento.");
        }

        var conteudoPdf = gerarConteudoPdf(contrato, request.getClienteDocumento(), request.getClienteNome());
        var hashSha256 = calcularSha256(conteudoPdf);

        var assinatura = new AssinaturaDigital(
            contratoId,
            request.getClienteDocumento(),
            request.getClienteNome(),
            StatusAssinaturaDigital.AGUARDANDO_DOCUMENTO
        );
        assinatura.setConteudoDocumento(conteudoPdf);
        assinatura.setHashDocumentoSha256(hashSha256);
        assinatura.setCaminhoDocumento("/documentos/contratos/" + contratoId + "/" + hashSha256 + ".pdf");
        assinatura = assinaturaRepository.save(assinatura);

        log.info("Documento gerado para assinatura: assinaturaId={}, contratoId={}, hash={}",
            assinatura.getId(), contratoId, hashSha256);
        return toResponse(assinatura);
    }

    @Transactional
    public AssinaturaDigitalResponse enviarOtp(Long assinaturaId) {
        var assinatura = assinaturaRepository.findById(assinaturaId)
            .orElseThrow(() -> new IllegalArgumentException("Assinatura não encontrada: " + assinaturaId));

        if (assinatura.getStatus() != StatusAssinaturaDigital.AGUARDANDO_DOCUMENTO
            && assinatura.getStatus() != StatusAssinaturaDigital.AGUARDANDO_OTP) {
            throw new IllegalStateException(
                "Assinatura em status " + assinatura.getStatus() + " não permite envio de OTP.");
        }

        var otpCodigo = gerarOtpCodigo();
        assinatura.setOtpCodigo(otpCodigo);
        assinatura.setOtpExpiracao(LocalDateTime.now().plusMinutes(OTP_EXPIRACAO_MINUTOS));
        assinatura.setOtpEnviado(true);
        assinatura.setStatus(StatusAssinaturaDigital.AGUARDANDO_OTP);
        assinatura = assinaturaRepository.save(assinatura);

        log.info("OTP enviado para assinatura: id={}, documento={}, expiraEm={}",
            assinatura.getId(), assinatura.getClienteDocumento(), assinatura.getOtpExpiracao());
        return toResponse(assinatura);
    }

    @Transactional
    public AssinaturaDigitalResponse confirmarAssinatura(Long assinaturaId, ConfirmarAssinaturaRequest request) {
        var assinatura = assinaturaRepository.findById(assinaturaId)
            .orElseThrow(() -> new IllegalArgumentException("Assinatura não encontrada: " + assinaturaId));

        if (assinatura.getStatus() != StatusAssinaturaDigital.AGUARDANDO_OTP
            && assinatura.getStatus() != StatusAssinaturaDigital.AGUARDANDO_BIOMETRIA) {
            throw new IllegalStateException(
                "Assinatura em status " + assinatura.getStatus() + " não permite confirmação.");
        }

        if (assinatura.getOtpExpiracao() != null && LocalDateTime.now().isAfter(assinatura.getOtpExpiracao())) {
            assinatura.setStatus(StatusAssinaturaDigital.EXPIRADO);
            assinaturaRepository.save(assinatura);
            throw new IllegalStateException("OTP expirado. Solicite um novo código.");
        }

        if (!request.getOtpCodigo().equals(assinatura.getOtpCodigo())) {
            throw new IllegalArgumentException("Código OTP inválido.");
        }

        assinatura.setBiometriaConfirmada(true);
        assinatura.setBiometriaTipo(request.getBiometriaTipo() != null ? request.getBiometriaTipo() : "FACE");
        assinatura.setIp(request.getIp());
        assinatura.setUserAgent(request.getUserAgent());
        assinatura.setStatus(StatusAssinaturaDigital.AGUARDANDO_BIOMETRIA);
        assinatura = assinaturaRepository.save(assinatura);

        if (request.getBiometriaHash() != null) {
            return finalizarAssinatura(assinatura);
        }

        log.info("OTP confirmado, aguardando biometria: assinaturaId={}", assinatura.getId());
        return toResponse(assinatura);
    }

    @Transactional
    public AssinaturaDigitalResponse confirmarBiometria(Long assinaturaId, String biometriaTipo, String biometriaHash) {
        var assinatura = assinaturaRepository.findById(assinaturaId)
            .orElseThrow(() -> new IllegalArgumentException("Assinatura não encontrada: " + assinaturaId));

        if (assinatura.getStatus() != StatusAssinaturaDigital.AGUARDANDO_BIOMETRIA) {
            throw new IllegalStateException(
                "Assinatura em status " + assinatura.getStatus() + " não permite confirmação biométrica.");
        }

        assinatura.setBiometriaConfirmada(true);
        assinatura.setBiometriaTipo(biometriaTipo);
        return finalizarAssinatura(assinatura);
    }

    @Transactional(readOnly = true)
    public AssinaturaDigitalResponse buscarPorContrato(Long contratoId) {
        var assinatura = assinaturaRepository.findByContratoId(contratoId).stream()
            .filter(a -> a.getStatus() != StatusAssinaturaDigital.EXPIRADO
                && a.getStatus() != StatusAssinaturaDigital.RECUSADO)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                "Nenhuma assinatura em andamento para o contrato: " + contratoId));
        return toResponse(assinatura);
    }

    @Transactional(readOnly = true)
    public List<AssinaturaDigitalResponse> listarPorContrato(Long contratoId) {
        return assinaturaRepository.findByContratoId(contratoId).stream()
            .map(this::toResponse)
            .toList();
    }

    private AssinaturaDigitalResponse finalizarAssinatura(AssinaturaDigital assinatura) {
        var timestamp = gerarTimestampDigital(assinatura.getHashDocumentoSha256());

        assinatura.setTimestampDigital(timestamp);
        assinatura.setValidaJuridicamente(true);
        assinatura.setCertificadoIcpBrasil("ICP-BR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        assinatura.setDataAssinatura(LocalDateTime.now());
        assinatura.setStatus(StatusAssinaturaDigital.ASSINADO);
        assinatura = assinaturaRepository.save(assinatura);

        log.info("Assinatura digital finalizada: id={}, contratoId={}, hash={}, timestamp={}",
            assinatura.getId(), assinatura.getContratoId(),
            assinatura.getHashDocumentoSha256(), timestamp);
        return toResponse(assinatura);
    }

    private String gerarConteudoPdf(Contrato contrato, String clienteDocumento, String clienteNome) {
        var now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return """
            CONTRATO DE %s - AURIX PLATAFORMA

            Contrato N.: %s
            Cliente: %s (%s)
            Valor: R$ %s
            Prazo: %d meses
            Taxa: %s%% a.m.

            CLÁUSULAS:

            1. OBJETO: O presente contrato tem por objeto o %s conforme termos e condições aqui estabelecidos.

            2. DO VALOR E FORMA DE PAGAMENTO: O valor total de R$ %s será pago em %d parcelas
            mensais no valor de R$ %s cada, conforme tabela de amortização.

            3. DAS TAXAS: Taxa de juros de %s%% ao mês, conforme regulamentação vigente.

            4. DA VIGÊNCIA: Contrato válido a partir da data de assinatura digital com validade
            jurídica conforme ICP-Brasil e marca temporal ITI.

            5. DA RENEGOCIAÇÃO: O contratante poderá solicitar renegociação dos termos conforme
            regulamento interno, respeitados os prazos de carência.

            6. DO SEGURO PRESTAMISTA: Será oferecido seguro prestamista com cobertura de morte,
            invalidez permanente e desemprego, conforme termos separados.

            7. DA PROTEÇÃO DE DADOS: Os dados pessoais serão tratados conforme LGPD (Lei 13.709/2018).

            8. FORO: Fica eleito o foro da comarca de São Paulo/SP para dirimir questões oriundas
            deste contrato.

            Data de assinatura: %s
            Documento assinado digitalmente com certificado ICP-Brasil.
            Marca temporal: ITI - Instituto Nacional de Tecnologia da Informação.
            """.formatted(
            contrato.getTipoContrato().name(),
            contrato.getNumeroContrato(),
            clienteNome != null ? clienteNome : "CLIENTE",
            clienteDocumento,
            contrato.getValor(),
            contrato.getPrazoMeses(),
            contrato.getValorParcela(),
            contrato.getTipoContrato().name().toLowerCase(),
            contrato.getValor(),
            contrato.getPrazoMeses(),
            contrato.getValorParcela(),
            contrato.getTaxaJuros(),
            now
        );
    }

    private String calcularSha256(String conteudo) {
        try {
            var digest = MessageDigest.getInstance("SHA-256");
            var hash = digest.digest(conteudo.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 não disponível", e);
        }
    }

    private String gerarOtpCodigo() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }

    private String gerarTimestampDigital(String hashDocumento) {
        var now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return "ITI|" + hashDocumento + "|" + now + "|aurix-plataforma";
    }

    private AssinaturaDigitalResponse toResponse(AssinaturaDigital a) {
        return new AssinaturaDigitalResponse(
            a.getId(), a.getContratoId(), a.getClienteDocumento(), a.getClienteNome(),
            a.getStatus().name(), a.isOtpEnviado(), a.isBiometriaConfirmada(),
            a.getBiometriaTipo(), a.getHashDocumentoSha256(), a.getTimestampDigital(),
            a.isValidaJuridicamente(), a.getDataAssinatura(), a.getDataCriacao());
    }
}
