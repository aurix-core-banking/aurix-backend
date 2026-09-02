package com.aurix.platform.customer.integration;

import com.aurix.platform.customer.onboarding.service.BureauProvider;
import com.aurix.platform.customer.onboarding.service.BureauService;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Cliente de consulta a bureau de crédito (Serasa/SPC/Quod).
 * <p>
 * Delega para os {@link BureauProvider} ativos em ordem definida por
 * {@link org.springframework.core.annotation.Order} (Serasa &gt; Quod &gt; Stub),
 * tentando o próximo provedor quando o atual falha. No perfil dev/test o stub
 * atua apenas como último recurso; em producao apenas os provedores reais
 * (Serasa e Quod) estão ativos. Retorna o score como String.
 */
@Component
public class BureauClient {

    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(BureauClient.class);

    private final List<BureauProvider> bureauProviders;

    public BureauClient(List<BureauProvider> bureauProviders) {
        this.bureauProviders = bureauProviders;
    }

    public String consultarScore(String cpf) {
        RuntimeException ultimaFalha = null;
        for (BureauProvider provider : bureauProviders) {
            try {
                String score = resultadoScore(provider.consultar(cpf));
                if (score != null) {
                    return score;
                }
                ultimaFalha = new RuntimeException("Provedor " + provider.getClass().getSimpleName()
                        + " retornou resultado sem score");
            } catch (RuntimeException e) {
                log.warn("Falha no provedor de score {}: {} — tentando próximo",
                        provider.getClass().getSimpleName(), e.getMessage());
                ultimaFalha = e;
            }
        }
        throw ultimaFalha != null ? ultimaFalha
                : new RuntimeException("Nenhum provedor de bureau de crédito configurado");
    }

    private String resultadoScore(BureauService.ResultadoBureau resultado) {
        return resultado != null && resultado.score() != null
                ? resultado.score().toString()
                : null;
    }
}