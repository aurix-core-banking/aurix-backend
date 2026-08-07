package com.aurix.platform.banking.core.service;

import com.aurix.platform.shared.repository.ClienteRepository;
import com.aurix.platform.shared.dto.ClienteDTO;
import com.aurix.platform.shared.entity.Cliente;
import com.aurix.platform.shared.exception.ClienteNaoEncontradoException;
import com.aurix.platform.shared.tenant.TenantContext;
import com.aurix.platform.shared.util.CNPJUtil;
import com.aurix.platform.shared.util.CPFUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gestão de clientes
 */
@Service
@Transactional
public class ClienteService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ClienteService.class);
    private final ClienteRepository clienteRepository;

    /**
     * Cria um novo cliente
     */
    public ClienteDTO criarCliente(ClienteDTO clienteDTO) {
        if (clienteDTO.getTipoPessoa() == Cliente.TipoPessoa.FISICA) {
            log.info("Criando cliente PF com CPF: {}", clienteDTO.getCpf());
            if (clienteDTO.getCpf() == null || !CPFUtil.isValid(clienteDTO.getCpf())) {
                throw new IllegalArgumentException("CPF inválido: " + clienteDTO.getCpf());
            }
        } else if (clienteDTO.getTipoPessoa() == Cliente.TipoPessoa.JURIDICA) {
            log.info("Criando cliente PJ com CNPJ: {}", clienteDTO.getCnpj());
            if (clienteDTO.getCnpj() == null || !CNPJUtil.isValid(clienteDTO.getCnpj())) {
                throw new IllegalArgumentException("CNPJ inválido: " + clienteDTO.getCnpj());
            }
        } else {
            throw new IllegalArgumentException("Tipo de pessoa é obrigatório");
        }
        String tenantId = TenantContext.getTenantId();
        if (clienteDTO.getTipoPessoa() == Cliente.TipoPessoa.FISICA) {
            if (clienteRepository.existsByTenantIdAndCpf(tenantId, clienteDTO.getCpf())) {
                throw new IllegalArgumentException("Cliente com CPF " + clienteDTO.getCpf() + " já existe");
            }
        } else {
            if (clienteRepository.existsByTenantIdAndCnpj(tenantId, clienteDTO.getCnpj())) {
                throw new IllegalArgumentException("Cliente com CNPJ " + clienteDTO.getCnpj() + " já existe");
            }
        }
        if (clienteRepository.existsByTenantIdAndEmail(tenantId, clienteDTO.getEmail())) {
            throw new IllegalArgumentException("Cliente com email " + clienteDTO.getEmail() + " já existe");
        }
        Cliente cliente = new Cliente();
        cliente.setTenantId(tenantId);
        cliente.setTipoPessoa(clienteDTO.getTipoPessoa());
        if (clienteDTO.getTipoPessoa() == Cliente.TipoPessoa.FISICA) {
            cliente.setCpf(clienteDTO.getCpf());
            cliente.setNome(clienteDTO.getNome());
            cliente.setDataNascimento(clienteDTO.getDataNascimento());
        } else {
            cliente.setCnpj(clienteDTO.getCnpj());
            cliente.setNomeRazaoSocial(clienteDTO.getNomeRazaoSocial());
            cliente.setNomeFantasia(clienteDTO.getNomeFantasia());
            cliente.setInscricaoEstadual(clienteDTO.getInscricaoEstadual());
            cliente.setInscricaoMunicipal(clienteDTO.getInscricaoMunicipal());
        }
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setEndereco(clienteDTO.getEndereco());
        cliente.setCidade(clienteDTO.getCidade());
        cliente.setEstado(clienteDTO.getEstado());
        cliente.setCep(clienteDTO.getCep());
        cliente.setContato(clienteDTO.getContato());
        cliente.setFaturamentoMensal(clienteDTO.getFaturamentoMensal());
        cliente.setCapitalSocial(clienteDTO.getCapitalSocial());
        cliente.setCnaePrincipal(clienteDTO.getCnaePrincipal());
        cliente.setPorte(clienteDTO.getPorte());
        cliente.setDataConstituicao(clienteDTO.getDataConstituicao());
        cliente.setStatus(Cliente.StatusCliente.ATIVO);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        log.info("Cliente criado com ID: {}", clienteSalvo.getId());
        return converterParaDTO(clienteSalvo);
    }

    /**
     * Busca cliente por ID
     */
    @Transactional(readOnly = true)
    public ClienteDTO buscarClientePorId(Long id) {
        log.info("Buscando cliente por ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Cliente cliente = clienteRepository.findByTenantIdAndId(tenantId, id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
        return converterParaDTO(cliente);
    }

    @Transactional(readOnly = true)
    public ClienteDTO buscarClientePorCpf(String cpf) {
        log.info("Buscando cliente por CPF: {}", cpf);
        if (!CPFUtil.isValid(cpf)) {
            throw new IllegalArgumentException("CPF inválido: " + cpf);
        }
        String tenantId = TenantContext.getTenantId();
        Cliente cliente = clienteRepository.findByTenantIdAndCpf(tenantId, cpf).orElseThrow(() -> new ClienteNaoEncontradoException(cpf));
        return converterParaDTO(cliente);
    }

    @Transactional(readOnly = true)
    public ClienteDTO buscarClientePorCnpj(String cnpj) {
        log.info("Buscando cliente por CNPJ: {}", cnpj);
        if (!CNPJUtil.isValid(cnpj)) {
            throw new IllegalArgumentException("CNPJ inválido: " + cnpj);
        }
        String tenantId = TenantContext.getTenantId();
        Cliente cliente = clienteRepository.findByTenantIdAndCnpj(tenantId, CNPJUtil.unformat(cnpj))
            .orElseThrow(() -> new ClienteNaoEncontradoException(cnpj, true));
        return converterParaDTO(cliente);
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> listarClientes() {
        log.info("Listando todos os clientes");
        String tenantId = TenantContext.getTenantId();
        return clienteRepository.findByTenantId(tenantId).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> listarClientesAtivos() {
        log.info("Listando clientes ativos");
        String tenantId = TenantContext.getTenantId();
        return clienteRepository.findByTenantIdAndStatus(tenantId, Cliente.StatusCliente.ATIVO).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    public ClienteDTO atualizarCliente(Long id, ClienteDTO clienteDTO) {
        log.info("Atualizando cliente ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Cliente cliente = clienteRepository.findByTenantIdAndId(tenantId, id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
        cliente.setTipoPessoa(clienteDTO.getTipoPessoa());
        if (clienteDTO.getTipoPessoa() == Cliente.TipoPessoa.FISICA) {
            cliente.setCpf(clienteDTO.getCpf());
            cliente.setNome(clienteDTO.getNome());
            cliente.setDataNascimento(clienteDTO.getDataNascimento());
        } else {
            cliente.setCnpj(clienteDTO.getCnpj());
            cliente.setNomeRazaoSocial(clienteDTO.getNomeRazaoSocial());
            cliente.setNomeFantasia(clienteDTO.getNomeFantasia());
            cliente.setInscricaoEstadual(clienteDTO.getInscricaoEstadual());
            cliente.setInscricaoMunicipal(clienteDTO.getInscricaoMunicipal());
        }
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setEndereco(clienteDTO.getEndereco());
        cliente.setCidade(clienteDTO.getCidade());
        cliente.setEstado(clienteDTO.getEstado());
        cliente.setCep(clienteDTO.getCep());
        cliente.setContato(clienteDTO.getContato());
        cliente.setFaturamentoMensal(clienteDTO.getFaturamentoMensal());
        cliente.setCapitalSocial(clienteDTO.getCapitalSocial());
        cliente.setCnaePrincipal(clienteDTO.getCnaePrincipal());
        cliente.setPorte(clienteDTO.getPorte());
        cliente.setDataConstituicao(clienteDTO.getDataConstituicao());
        Cliente clienteAtualizado = clienteRepository.save(cliente);
        log.info("Cliente atualizado com sucesso");
        return converterParaDTO(clienteAtualizado);
    }

    /**
     * Inativa cliente
     */
    public void inativarCliente(Long id) {
        log.info("Inativando cliente ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Cliente cliente = clienteRepository.findByTenantIdAndId(tenantId, id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
        cliente.setStatus(Cliente.StatusCliente.INATIVO);
        clienteRepository.save(cliente);
        log.info("Cliente inativado com sucesso");
    }

    /**
     * Ativa cliente
     */
    public void ativarCliente(Long id) {
        log.info("Ativando cliente ID: {}", id);
        String tenantId = TenantContext.getTenantId();
        Cliente cliente = clienteRepository.findByTenantIdAndId(tenantId, id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
        cliente.setStatus(Cliente.StatusCliente.ATIVO);
        clienteRepository.save(cliente);
        log.info("Cliente ativado com sucesso");
    }

    /**
     * Converte entidade para DTO
     */
    private ClienteDTO converterParaDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setTipoPessoa(cliente.getTipoPessoa());
        dto.setCpf(cliente.getCpf());
        dto.setNome(cliente.getNome());
        dto.setCnpj(cliente.getCnpj());
        dto.setNomeRazaoSocial(cliente.getNomeRazaoSocial());
        dto.setNomeFantasia(cliente.getNomeFantasia());
        dto.setInscricaoEstadual(cliente.getInscricaoEstadual());
        dto.setInscricaoMunicipal(cliente.getInscricaoMunicipal());
        dto.setEmail(cliente.getEmail());
        dto.setTelefone(cliente.getTelefone());
        dto.setDataNascimento(cliente.getDataNascimento());
        dto.setEndereco(cliente.getEndereco());
        dto.setCidade(cliente.getCidade());
        dto.setEstado(cliente.getEstado());
        dto.setCep(cliente.getCep());
        dto.setContato(cliente.getContato());
        dto.setFaturamentoMensal(cliente.getFaturamentoMensal());
        dto.setCapitalSocial(cliente.getCapitalSocial());
        dto.setCnaePrincipal(cliente.getCnaePrincipal());
        dto.setPorte(cliente.getPorte());
        dto.setDataConstituicao(cliente.getDataConstituicao());
        dto.setStatus(cliente.getStatus());
        dto.setDataCriacao(cliente.getDataCriacao() != null ? cliente.getDataCriacao().toString() : null);
        dto.setDataAtualizacao(cliente.getDataAtualizacao() != null ? cliente.getDataAtualizacao().toString() : null);
        return dto;
    }

    @java.lang.SuppressWarnings("all")
    public ClienteService(final ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
}
