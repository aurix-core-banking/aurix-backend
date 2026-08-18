package com.aurix.platform.customer.temporal.activity;

import com.aurix.platform.customer.integration.BureauClient;
import com.aurix.platform.customer.integration.FraudClient;
import com.aurix.platform.customer.integration.KycClient;
import com.aurix.platform.customer.integration.PepClient;
import com.aurix.platform.customer.integration.ReceitaFederalClient;
import com.aurix.platform.customer.integration.CoreApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OnboardingPFActivitiesImpl implements OnboardingPFActivities {

    private static final Logger log = LoggerFactory.getLogger(OnboardingPFActivitiesImpl.class);

    private final ReceitaFederalClient receitaFederalClient;
    private final BureauClient bureauClient;
    private final PepClient pepClient;
    private final FraudClient fraudClient;
    private final KycClient kycClient;
    private final CoreApiClient coreApiClient;

    public OnboardingPFActivitiesImpl(ReceitaFederalClient receitaFederalClient,
                                       BureauClient bureauClient,
                                       PepClient pepClient,
                                       FraudClient fraudClient,
                                       KycClient kycClient,
                                       CoreApiClient coreApiClient) {
        this.receitaFederalClient = receitaFederalClient;
        this.bureauClient = bureauClient;
        this.pepClient = pepClient;
        this.fraudClient = fraudClient;
        this.kycClient = kycClient;
        this.coreApiClient = coreApiClient;
    }

    @Override
    public ResultadoConsulta consultarReceitaFederal(String cpf, String nome, String dataNascimento) {
        log.info("Activity: Consultando Receita Federal para CPF {}", cpf);
        try {
            boolean valido = receitaFederalClient.validarCpf(cpf, nome, dataNascimento);
            return new ResultadoConsulta(valido, valido ? "CPF válido" : "CPF inválido", null, null);
        } catch (Exception e) {
            log.error("Erro na consulta RF: {}", e.getMessage());
            return new ResultadoConsulta(false, "Erro na consulta: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResultadoConsulta consultarBureauCredito(String cpf) {
        log.info("Activity: Consultando bureau de crédito para CPF {}", cpf);
        try {
            String score = bureauClient.consultarScore(cpf);
            return new ResultadoConsulta(true, "Score consultado", score, null);
        } catch (Exception e) {
            log.error("Erro na consulta bureau: {}", e.getMessage());
            return new ResultadoConsulta(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResultadoConsulta verificarPep(String cpf, String nome) {
        log.info("Activity: Verificando PEP para CPF {}", cpf);
        try {
            boolean isPep = pepClient.verificarPep(cpf, nome);
            return new ResultadoConsulta(!isPep, isPep ? "Cliente é PEP" : "Não é PEP", null, null);
        } catch (Exception e) {
            return new ResultadoConsulta(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResultadoConsulta analisarFraude(String cpf, String nome, String ip, String dispositivo) {
        log.info("Activity: Análise de fraude para CPF {}", cpf);
        try {
            int risco = fraudClient.analisarRisco(cpf, nome, ip, dispositivo);
            return new ResultadoConsulta(risco <= 70, "Risco: " + risco, String.valueOf(risco), null);
        } catch (Exception e) {
            return new ResultadoConsulta(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResultadoConsulta validarKyc(String cpf, String documentoFrente, String documentoVerso, String selfie) {
        log.info("Activity: Validando KYC para CPF {}", cpf);
        try {
            boolean aprovado = kycClient.validarDocumentos(cpf, documentoFrente, documentoVerso, selfie);
            return new ResultadoConsulta(aprovado, aprovado ? "KYC aprovado" : "KYC rejeitado", null, null);
        } catch (Exception e) {
            return new ResultadoConsulta(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResultadoConsulta criarClienteConta(String cpf, String nome, String email, String telefone) {
        log.info("Activity: Criando cliente PF e conta para CPF {}", cpf);
        try {
            String clienteId = coreApiClient.criarClientePFeConta(cpf, nome, email, telefone);
            return new ResultadoConsulta(true, "Cliente e conta criados", clienteId, null);
        } catch (Exception e) {
            return new ResultadoConsulta(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public void publicarEvento(String topico, String chave, String payload) {
        log.info("Activity: Publicando evento no tópico {}", topico);
        // Kafka publish via EventHub
    }
}
