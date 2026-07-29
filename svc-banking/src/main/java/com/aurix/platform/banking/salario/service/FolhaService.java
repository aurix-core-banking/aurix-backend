package com.aurix.platform.banking.salario.service;

import com.aurix.platform.banking.salario.client.ContaCorrenteClient;
import com.aurix.platform.banking.salario.dto.CreditoDiretoRequest;
import com.aurix.platform.shared.event.Topics;
import com.aurix.platform.banking.salario.entity.ContaSalario;
import com.aurix.platform.banking.salario.entity.FolhaPagamento;
import com.aurix.platform.banking.salario.entity.ItemFolhaPagamento;
import com.aurix.platform.banking.salario.event.SalarioCreditadoEvent;
import com.aurix.platform.banking.salario.repository.ContaSalarioRepository;
import com.aurix.platform.banking.salario.repository.FolhaPagamentoRepository;
import com.aurix.platform.banking.salario.repository.ItemFolhaPagamentoRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class FolhaService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FolhaService.class);

    private final FolhaPagamentoRepository folhaRepository;
    private final ItemFolhaPagamentoRepository itemRepository;
    private final ContaSalarioRepository contaSalarioRepository;
    private final ContaCorrenteClient contaCorrenteClient;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public FolhaService(FolhaPagamentoRepository folhaRepository,
                        ItemFolhaPagamentoRepository itemRepository,
                        ContaSalarioRepository contaSalarioRepository,
                        ContaCorrenteClient contaCorrenteClient,
                        KafkaTemplate<String, String> kafkaTemplate,
                        ObjectMapper objectMapper) {
        this.folhaRepository = folhaRepository;
        this.itemRepository = itemRepository;
        this.contaSalarioRepository = contaSalarioRepository;
        this.contaCorrenteClient = contaCorrenteClient;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void creditarDireto(CreditoDiretoRequest request) {
        log.info("Credito direto para CPF: {}", request.getCpfFuncionario());

        ContaSalario conta = contaSalarioRepository
            .findByTenantIdAndEmpresaIdAndCpfFuncionario(
                TenantContext.getTenantId(),
                request.getEmpresaId(), request.getCpfFuncionario()
            ).orElseThrow(() -> new IllegalArgumentException(
                "Conta salario nao encontrada para CPF " + request.getCpfFuncionario()));

        contaCorrenteClient.creditar(conta.getContaCorrenteId(),
            new ContaCorrenteClient.CreditoRequest(
                request.getValorLiquido(), "Salario - credito direto"));

        publicarEventoCredito(conta, request.getValorLiquido(), "DIRETO", request.getEmpresaId());

        log.info("Credito direto realizado: conta={}, valor={}", conta.getId(), request.getValorLiquido());
    }

    public void creditarItem(ItemFolhaPagamento item, ContaSalario conta, Long empresaId) {
        try {
            contaCorrenteClient.creditar(conta.getContaCorrenteId(),
                new ContaCorrenteClient.CreditoRequest(
                    item.getValorLiquido(), "Salario - CNAB"));
            if (conta.getPortabilidadeAtiva()) {
                item.setStatus(ItemFolhaPagamento.StatusItem.PORTADO);
            } else {
                item.setStatus(ItemFolhaPagamento.StatusItem.CREDITADO);
            }
            itemRepository.save(item);

            publicarEventoCredito(conta, item.getValorLiquido(), "CNAB", empresaId);

        } catch (Exception e) {
            log.error("Erro ao creditar item folha {}: {}", item.getId(), e.getMessage());
            item.setStatus(ItemFolhaPagamento.StatusItem.ERRO);
            itemRepository.save(item);
        }
    }

    public void processarFolha(FolhaPagamento folha) {
        List<ItemFolhaPagamento> itens = itemRepository.findByFolhaId(folha.getId());

        for (ItemFolhaPagamento item : itens) {
            contaSalarioRepository
                .findByTenantIdAndEmpresaIdAndCpfFuncionario(
                    folha.getTenantId(), folha.getEmpresaId(), item.getCpfFuncionario()
                ).ifPresentOrElse(
                    conta -> creditarItem(item, conta, folha.getEmpresaId()),
                    () -> {
                        log.warn("Conta salario nao encontrada para CPF {} na folha {}",
                            item.getCpfFuncionario(), folha.getId());
                        item.setStatus(ItemFolhaPagamento.StatusItem.ERRO);
                        itemRepository.save(item);
                    }
                );
        }

        folha.setStatus(FolhaPagamento.StatusFolha.PROCESSADO);
        folhaRepository.save(folha);
        log.info("Folha processada: {} itens, {} funcionarios", folha.getId(), itens.size());
    }

    @Transactional(readOnly = true)
    public List<FolhaPagamento> listarFolhasPendentes() {
        return folhaRepository.findByTenantIdAndStatus(
            TenantContext.getTenantId(),
            FolhaPagamento.StatusFolha.VALIDADO);
    }

    private void publicarEventoCredito(ContaSalario conta, BigDecimal valor, String tipo, Long empresaId) {
        try {
            String json = objectMapper.writeValueAsString(new SalarioCreditadoEvent(
                conta.getId(), valor, tipo, empresaId, LocalDate.now()));
            kafkaTemplate.send(Topics.SALARIO_CREDITADO, json);
        } catch (Exception e) {
            log.warn("Falha ao publicar evento credito: {}", e.getMessage());
        }
    }
}
