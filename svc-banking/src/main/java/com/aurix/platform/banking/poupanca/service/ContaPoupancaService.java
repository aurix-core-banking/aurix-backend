package com.aurix.platform.banking.poupanca.service;

import com.aurix.platform.banking.poupanca.dto.ContaPoupancaResponse;
import com.aurix.platform.banking.poupanca.dto.CriarContaRequest;
import com.aurix.platform.banking.poupanca.entity.ContaPoupanca;
import com.aurix.platform.banking.poupanca.entity.ContaPoupanca.StatusConta;
import com.aurix.platform.banking.poupanca.event.ContaPoupancaEvent;
import com.aurix.platform.banking.poupanca.repository.ContaPoupancaRepository;
import com.aurix.platform.shared.event.Topics;
import com.aurix.platform.shared.tenant.TenantContext;
import com.aurix.platform.shared.util.TransacaoUtil;
import java.time.LocalDate;
import java.util.List;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContaPoupancaService {

    private final ContaPoupancaRepository contaPoupancaRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ContaPoupancaService(ContaPoupancaRepository contaPoupancaRepository,
                                KafkaTemplate<String, Object> kafkaTemplate) {
        this.contaPoupancaRepository = contaPoupancaRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public ContaPoupancaResponse criarConta(CriarContaRequest request) {
        ContaPoupanca conta = new ContaPoupanca();
        conta.setClienteId(request.getClienteId());
        conta.setContaCorrenteId(request.getContaCorrenteId());
        conta.setNumeroConta(TransacaoUtil.gerarCodigoPix());
        conta.setAniversarioDia(request.getAniversarioDia() > 0 ? request.getAniversarioDia() : LocalDate.now().getDayOfMonth());
        conta.setDataAbertura(LocalDate.now());
        conta.setTenantId(TenantContext.getTenantId());
        conta = contaPoupancaRepository.save(conta);

        kafkaTemplate.send(Topics.POUPANCA_CONTA_CRIADA, new ContaPoupancaEvent(
            conta.getId(), conta.getClienteId(), conta.getNumeroConta(),
            conta.getDataAbertura(), conta.getTenantId()));

        return toResponse(conta);
    }

    @Transactional(readOnly = true)
    public ContaPoupancaResponse buscarPorId(Long id) {
        ContaPoupanca conta = contaPoupancaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Conta poupanca nao encontrada: " + id));
        return toResponse(conta);
    }

    @Transactional(readOnly = true)
    public List<ContaPoupancaResponse> listarPorCliente(Long clienteId) {
        return contaPoupancaRepository.findByClienteId(clienteId).stream()
            .map(this::toResponse).toList();
    }

    public void bloquear(Long id) {
        ContaPoupanca conta = contaPoupancaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Conta poupanca nao encontrada: " + id));
        if (conta.getStatus() == StatusConta.ENCERRADA) {
            throw new IllegalStateException("Conta poupanca ja esta encerrada");
        }
        conta.setStatus(StatusConta.BLOQUEADA);
        contaPoupancaRepository.save(conta);
    }

    public void encerrar(Long id) {
        ContaPoupanca conta = contaPoupancaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Conta poupanca nao encontrada: " + id));
        if (conta.getSaldo().compareTo(java.math.BigDecimal.ZERO) > 0) {
            throw new IllegalStateException("Conta poupanca possui saldo — transfira antes de encerrar");
        }
        conta.setStatus(StatusConta.ENCERRADA);
        contaPoupancaRepository.save(conta);
    }

    private ContaPoupancaResponse toResponse(ContaPoupanca conta) {
        ContaPoupancaResponse r = new ContaPoupancaResponse();
        r.setId(conta.getId());
        r.setClienteId(conta.getClienteId());
        r.setContaCorrenteId(conta.getContaCorrenteId());
        r.setNumeroConta(conta.getNumeroConta());
        r.setSaldo(conta.getSaldo());
        r.setAniversarioDia(conta.getAniversarioDia());
        r.setDataAbertura(conta.getDataAbertura());
        r.setUltimoAniversario(conta.getUltimoAniversario());
        r.setStatus(conta.getStatus().name());
        r.setDataCriacao(conta.getDataCriacao());
        return r;
    }
}
