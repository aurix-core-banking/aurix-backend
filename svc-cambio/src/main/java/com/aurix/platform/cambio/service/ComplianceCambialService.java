package com.aurix.platform.cambio.service;

import com.aurix.platform.cambio.client.ComplianceClient;
import com.aurix.platform.cambio.entity.ClienteCambio;
import com.aurix.platform.cambio.entity.ContratoCambio;
import com.aurix.platform.cambio.entity.Remessa;
import com.aurix.platform.cambio.provider.BacenCambialProvider;
import com.aurix.platform.cambio.repository.ClienteCambioRepository;
import com.aurix.platform.cambio.repository.RemessaRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComplianceCambialService {

    private static final Logger log = LoggerFactory.getLogger(ComplianceCambialService.class);

    private static final Set<String> FINALIDADES_PERMITIDAS = Set.of(
        "turismo", "educacao", "saude", "negocio", "familiar"
    );

    private final ClienteCambioRepository clienteCambioRepository;
    private final RemessaRepository remessaRepository;
    private final ComplianceClient complianceClient;
    private final BacenCambialProvider bacenCambialProvider;

    public ComplianceCambialService(ClienteCambioRepository clienteCambioRepository,
                                    RemessaRepository remessaRepository,
                                    ComplianceClient complianceClient,
                                    BacenCambialProvider bacenCambialProvider) {
        this.clienteCambioRepository = clienteCambioRepository;
        this.remessaRepository = remessaRepository;
        this.complianceClient = complianceClient;
        this.bacenCambialProvider = bacenCambialProvider;
    }

    public void validarLimitesCliente(Long clienteId, BigDecimal valor, String moeda) {
        ClienteCambio cliente = clienteCambioRepository.findByClienteId(clienteId)
            .orElseThrow(() -> new IllegalArgumentException("Cliente cambio nao habilitado: " + clienteId));

        LocalDate now = LocalDate.now();
        BigDecimal totalMes = BigDecimal.ZERO;
        BigDecimal totalAno = BigDecimal.ZERO;

        List<Remessa> remessas = remessaRepository.findByClienteId(clienteId);
        for (Remessa r : remessas) {
            if (r.getDataSolicitacao() != null && r.getStatus() != null && !"CANCELADA".equals(r.getStatus())) {
                LocalDate data = r.getDataSolicitacao().toLocalDate();
                if (data.getYear() == now.getYear() && data.getMonth() == now.getMonth()) {
                    totalMes = totalMes.add(r.getValor());
                }
                if (data.getYear() == now.getYear()) {
                    totalAno = totalAno.add(r.getValor());
                }
            }
        }

        BigDecimal saldoMensal = cliente.getLimiteRemessaMensal().subtract(totalMes);
        BigDecimal saldoAnual = cliente.getLimiteRemessaAnual().subtract(totalAno);

        if (valor.compareTo(saldoMensal) > 0) {
            throw new IllegalStateException("Limite mensal de remessa excedido para cliente: " + clienteId);
        }
        if (valor.compareTo(saldoAnual) > 0) {
            throw new IllegalStateException("Limite anual de remessa excedido para cliente: " + clienteId);
        }
    }

    public void validarFinalidade(String finalidade) {
        if (finalidade == null || !FINALIDADES_PERMITIDAS.contains(finalidade.toLowerCase())) {
            throw new IllegalArgumentException("Finalidade nao permitida: " + finalidade);
        }
    }

    public void registrarOperacaoBacen(ContratoCambio contrato) {
        try {
            complianceClient.registrarOperacao(new ComplianceClient.RegistrarOperacaoRequest(
                contrato.getId(), contrato.getClienteId(), contrato.getTipo(),
                contrato.getValorOrigem(), contrato.getMoedaOrigem()
            ));
            BacenCambialProvider.RegistroBacenResultado resultado =
                bacenCambialProvider.registrarRdeRof(contrato, null);
            if (resultado == null || resultado.protocolo() == null || resultado.protocolo().isBlank()) {
                throw new IllegalStateException(
                    "BACEN não confirmou registro RDE-ROF/SISBACEN para o contrato " + contrato.getId());
            }
            contrato.setRegistroBACEN(resultado.protocolo());
            log.info("Operacao registrada no BACEN (RDE-ROF/SISBACEN) para contrato: {}, protocolo: {}",
                contrato.getId(), resultado.protocolo());
        } catch (Exception e) {
            log.error("Erro ao registrar operacao no BACEN para contrato: {}", contrato.getId(), e);
            throw new RuntimeException("Falha ao registrar operacao no BACEN", e);
        }
    }

    public Object consultarRoe(Long clienteId) {
        try {
            return complianceClient.consultarRoe(clienteId);
        } catch (Exception e) {
            log.error("Erro ao consultar ROE para cliente: {}", clienteId, e);
            throw new RuntimeException("Falha ao consultar ROE", e);
        }
    }
}
