package com.aurix.platform.banking.core.service;

import com.aurix.platform.shared.repository.ClienteRepository;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.shared.dto.ContaDTO;
import com.aurix.platform.shared.entity.Cliente;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.exception.ClienteNaoEncontradoException;
import com.aurix.platform.shared.exception.ContaNaoEncontradaException;
import com.aurix.platform.shared.exception.SaldoInsuficienteException;
import com.aurix.platform.shared.tenant.TenantContext;
import com.aurix.platform.shared.util.ContaUtil;
import com.aurix.platform.shared.event.ContaEvent;
import com.aurix.platform.shared.event.EventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContaService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ContaService.class);
    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;
    private final EventPublisher eventPublisher;

    /**
     * Cria uma nova conta
     */
    public ContaDTO criarConta(ContaDTO contaDTO) {
        log.info("Criando conta para cliente ID: {}", contaDTO.getClienteId());
        String tenantId = TenantContext.getTenantId();
        Cliente cliente = clienteRepository.findByTenantIdAndId(tenantId, contaDTO.getClienteId()).orElseThrow(() -> new ClienteNaoEncontradoException(contaDTO.getClienteId()));
        if (contaDTO.getTipoConta() == Conta.TipoConta.EMPRESARIAL
                && cliente.getTipoPessoa() != com.aurix.platform.shared.entity.Cliente.TipoPessoa.JURIDICA) {
            throw new IllegalArgumentException("Conta empresarial requer cliente pessoa juridica");
        }
        String numeroConta = gerarNumeroContaUnico();
        Conta conta = new Conta();
        conta.setTenantId(tenantId);
        conta.setNumeroConta(numeroConta);
        conta.setCliente(cliente);
        conta.setTipoConta(contaDTO.getTipoConta());
        conta.setSaldo(contaDTO.getSaldo() != null ? contaDTO.getSaldo() : BigDecimal.ZERO);
        conta.setLimiteCredito(contaDTO.getLimiteCredito() != null ? contaDTO.getLimiteCredito() : BigDecimal.ZERO);
        conta.setLimiteUtilizado(BigDecimal.ZERO);
        conta.setStatus(Conta.StatusConta.ATIVA);
        conta.setDadosExtras(contaDTO.getDadosExtras());
        Conta contaSalva = contaRepository.save(conta);
        log.info("Conta criada com número: {}", contaSalva.getNumeroConta());
        try {
            eventPublisher.publicarContaCriada(ContaEvent.contaCriada(String.valueOf(contaSalva.getId()), String.valueOf(contaSalva.getCliente().getId()), contaSalva.getSaldo(), contaSalva.getTipoConta() != null ? contaSalva.getTipoConta().name() : "CORRENTE"));
        } catch (Exception e) {
            log.warn("Falha ao publicar evento conta-criada: {}", e.getMessage());
        }
        return converterParaDTO(contaSalva);
    }

    /**
     * Busca conta por ID
     */
    @Transactional(readOnly = true)
    public ContaDTO buscarContaPorId(Long id) {
        log.info("Buscando conta por ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, id).orElseThrow(() -> new ContaNaoEncontradaException(id));
        return converterParaDTO(conta);
    }

    /**
     * Busca conta por número
     */
    @Transactional(readOnly = true)
    public ContaDTO buscarContaPorNumero(String numeroConta) {
        log.info("Buscando conta por número: {}", numeroConta);
        // Validar formato do número da conta
        if (!ContaUtil.isValid(numeroConta)) {
            throw new IllegalArgumentException("Número da conta inválido: " + numeroConta);
        }
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndNumeroConta(tenantId, numeroConta).orElseThrow(() -> new ContaNaoEncontradaException(numeroConta));
        return converterParaDTO(conta);
    }

    /**
     * Lista contas por cliente
     */
    @Transactional(readOnly = true)
    public List<ContaDTO> listarContasPorCliente(Long clienteId) {
        log.info("Listando contas do cliente ID: {}", clienteId);
        String tenantId = TenantContext.getTenantId();
        return contaRepository.findByTenantIdAndClienteId(tenantId, clienteId).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ContaDTO> listarContasAtivasPorCliente(Long clienteId) {
        log.info("Listando contas ativas do cliente ID: {}", clienteId);
        String tenantId = TenantContext.getTenantId();
        return contaRepository.findContasAtivasByTenantIdAndClienteId(tenantId, clienteId).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ContaDTO> listarContas() {
        log.info("Listando todas as contas");
        String tenantId = TenantContext.getTenantId();
        return contaRepository.findByTenantId(tenantId).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Atualiza conta
     */
    public ContaDTO atualizarConta(Long id, ContaDTO contaDTO) {
        log.info("Atualizando conta ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, id).orElseThrow(() -> new ContaNaoEncontradaException(id));
        // Atualizar campos permitidos
        conta.setLimiteCredito(contaDTO.getLimiteCredito());
        conta.setDadosExtras(contaDTO.getDadosExtras());
        Conta contaAtualizada = contaRepository.save(conta);
        log.info("Conta atualizada com sucesso");
        try {
            eventPublisher.publicarContaAtualizada(ContaEvent.contaAtualizada(String.valueOf(contaAtualizada.getId()), String.valueOf(contaAtualizada.getCliente().getId()), contaAtualizada.getSaldo(), contaAtualizada.getStatus() != null ? contaAtualizada.getStatus().name() : "ATIVA"));
        } catch (Exception e) {
            log.warn("Falha ao publicar evento conta-atualizada: {}", e.getMessage());
        }
        return converterParaDTO(contaAtualizada);
    }

    /**
     * Debita um valor do saldo da conta de forma atômica (ver ADR-0002).
     * A verificação de saldo suficiente e a escrita ocorrem na mesma instrução SQL,
     * eliminando a janela de corrida do antigo padrão "ler saldo, somar, salvar".
     *
     * @throws SaldoInsuficienteException se a conta não tiver saldo suficiente
     * @throws ContaNaoEncontradaException se a conta não existir (ou for de outro tenant)
     */
    public void debitarSaldo(Long contaId, BigDecimal valor) {
        log.info("Debitando {} da conta ID: {}", valor, contaId);
        String tenantId = TenantContext.getTenantId();
        int linhasAfetadas = contaRepository.debitarSaldoAtomico(tenantId, contaId, valor);
        if (linhasAfetadas == 0) {
            if (contaRepository.findByTenantIdAndId(tenantId, contaId).isEmpty()) {
                throw new ContaNaoEncontradaException(contaId);
            }
            throw new SaldoInsuficienteException(contaId, valor);
        }
        log.info("Débito realizado com sucesso na conta ID: {}", contaId);
    }

    /**
     * Credita um valor no saldo da conta de forma atômica (ver ADR-0002).
     *
     * @throws ContaNaoEncontradaException se a conta não existir (ou for de outro tenant)
     */
    public void creditarSaldo(Long contaId, BigDecimal valor) {
        log.info("Creditando {} na conta ID: {}", valor, contaId);
        String tenantId = TenantContext.getTenantId();
        int linhasAfetadas = contaRepository.creditarSaldoAtomico(tenantId, contaId, valor);
        if (linhasAfetadas == 0) {
            throw new ContaNaoEncontradaException(contaId);
        }
        log.info("Crédito realizado com sucesso na conta ID: {}", contaId);
    }

    /**
     * Aumenta o limite utilizado da conta de forma atômica, condicionado a haver
     * limite disponível suficiente (mesmo padrão de débito atômico do ADR-0002).
     *
     * @throws SaldoInsuficienteException se o limite disponível for insuficiente
     * @throws ContaNaoEncontradaException se a conta não existir (ou for de outro tenant)
     */
    public void utilizarLimite(Long contaId, BigDecimal valor) {
        log.info("Utilizando {} do limite da conta ID: {}", valor, contaId);
        String tenantId = TenantContext.getTenantId();
        int linhasAfetadas = contaRepository.utilizarLimiteAtomico(tenantId, contaId, valor);
        if (linhasAfetadas == 0) {
            if (contaRepository.findByTenantIdAndId(tenantId, contaId).isEmpty()) {
                throw new ContaNaoEncontradaException(contaId);
            }
            throw new SaldoInsuficienteException(contaId, valor);
        }
        log.info("Limite utilizado com sucesso na conta ID: {}", contaId);
    }

    /**
     * Libera (reduz) o limite utilizado da conta de forma atômica.
     */
    public void liberarLimite(Long contaId, BigDecimal valor) {
        log.info("Liberando {} do limite utilizado da conta ID: {}", valor, contaId);
        String tenantId = TenantContext.getTenantId();
        int linhasAfetadas = contaRepository.liberarLimiteAtomico(tenantId, contaId, valor);
        if (linhasAfetadas == 0) {
            throw new ContaNaoEncontradaException(contaId);
        }
        log.info("Limite liberado com sucesso na conta ID: {}", contaId);
    }

    /**
     * Fecha conta
     */
    public void fecharConta(Long id) {
        log.info("Fechando conta ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, id).orElseThrow(() -> new ContaNaoEncontradaException(id));
        conta.setStatus(Conta.StatusConta.FECHADA);
        conta.setDataFechamento(java.time.LocalDateTime.now());
        contaRepository.save(conta);
        log.info("Conta fechada com sucesso");
    }

    /**
     * Gera número de conta único
     */
    private String gerarNumeroContaUnico() {
        String numeroConta;
        int tentativas = 0;
        int maxTentativas = 100;
        do {
            numeroConta = ContaUtil.gerarNumeroConta();
            tentativas++;
            if (tentativas >= maxTentativas) {
                throw new RuntimeException("Não foi possível gerar número de conta único após " + maxTentativas + " tentativas");
            }
        } while (contaRepository.existsByTenantIdAndNumeroConta(TenantContext.getTenantId(), numeroConta));
        return numeroConta;
    }

    /**
     * Converte entidade para DTO
     */
    private ContaDTO converterParaDTO(Conta conta) {
        ContaDTO dto = new ContaDTO();
        dto.setId(conta.getId());
        dto.setNumeroConta(conta.getNumeroConta());
        dto.setClienteId(conta.getCliente().getId());
        dto.setClienteNome(conta.getCliente().getNome());
        dto.setTipoConta(conta.getTipoConta());
        dto.setSaldo(conta.getSaldo());
        dto.setLimiteCredito(conta.getLimiteCredito());
        dto.setLimiteUtilizado(conta.getLimiteUtilizado());
        dto.setLimiteDisponivel(conta.getLimiteDisponivel());
        dto.setStatus(conta.getStatus());
        dto.setClienteTipoPessoa(conta.getCliente().getTipoPessoa().name());
        dto.setDataAbertura(conta.getDataAbertura());
        dto.setDataFechamento(conta.getDataFechamento());
        dto.setDadosExtras(conta.getDadosExtras());
        dto.setDataCriacao(conta.getDataCriacao() != null ? conta.getDataCriacao().toString() : null);
        dto.setDataAtualizacao(conta.getDataAtualizacao() != null ? conta.getDataAtualizacao().toString() : null);
        return dto;
    }

    @java.lang.SuppressWarnings("all")
    public ContaService(final ContaRepository contaRepository, final ClienteRepository clienteRepository, final EventPublisher eventPublisher) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
        this.eventPublisher = eventPublisher;
    }
}
