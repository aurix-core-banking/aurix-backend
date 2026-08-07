package com.aurix.platform.customer.service;

import com.aurix.platform.customer.entity.Cliente;
import com.aurix.platform.customer.entity.Endereco;
import com.aurix.platform.customer.entity.Contato;
import com.aurix.platform.customer.repository.ClienteCadastroRepository;
import com.aurix.platform.customer.repository.EnderecoRepository;
import com.aurix.platform.customer.repository.ContatoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ClienteService {
    private final ClienteCadastroRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final ContatoRepository contatoRepository;
    private final ClienteProducer clienteProducer;

    public ClienteService(ClienteCadastroRepository clienteRepository, EnderecoRepository enderecoRepository,
                          ContatoRepository contatoRepository, ClienteProducer clienteProducer) {
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.contatoRepository = contatoRepository;
        this.clienteProducer = clienteProducer;
    }

    @Transactional(readOnly = true)
    public List<Cliente> listar(String segmento, String status) {
        if (segmento != null) return clienteRepository.findBySegmento(segmento);
        if (status != null) return clienteRepository.findByStatus(status);
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente nao encontrado: " + id));
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorDocumento(String documento) {
        return clienteRepository.findByDocumento(documento)
                .orElseThrow(() -> new IllegalArgumentException("Cliente nao encontrado: " + documento));
    }

    public Cliente criar(Cliente cliente) {
        cliente.setStatus("ATIVO");
        Cliente saved = clienteRepository.save(cliente);
        clienteProducer.clienteCriado(saved);
        return saved;
    }

    public Cliente atualizar(Long id, Cliente dados) {
        Cliente existente = buscarPorId(id);
        String statusAnterior = existente.getStatus();
        if (dados.getNomeCompleto() != null) existente.setNomeCompleto(dados.getNomeCompleto());
        if (dados.getEmail() != null) existente.setEmail(dados.getEmail());
        if (dados.getTelefone() != null) existente.setTelefone(dados.getTelefone());
        if (dados.getSegmento() != null) existente.setSegmento(dados.getSegmento());
        if (dados.getStatus() != null) existente.setStatus(dados.getStatus());
        if (dados.getObservacao() != null) existente.setObservacao(dados.getObservacao());
        Cliente saved = clienteRepository.save(existente);
        clienteProducer.clienteAtualizado(saved);
        if (dados.getStatus() != null && !dados.getStatus().equals(statusAnterior)) {
            clienteProducer.clienteStatusAlterado(saved, statusAnterior);
        }
        return saved;
    }

    @Transactional(readOnly = true)
    public List<Endereco> listarEnderecos(Long clienteId) {
        return enderecoRepository.findByClienteId(clienteId);
    }

    public Endereco adicionarEndereco(Long clienteId, Endereco endereco) {
        Cliente cliente = buscarPorId(clienteId);
        endereco.setCliente(cliente);
        return enderecoRepository.save(endereco);
    }

    @Transactional(readOnly = true)
    public List<Contato> listarContatos(Long clienteId) {
        return contatoRepository.findByClienteId(clienteId);
    }

    public Contato adicionarContato(Long clienteId, Contato contato) {
        Cliente cliente = buscarPorId(clienteId);
        contato.setCliente(cliente);
        return contatoRepository.save(contato);
    }
}
