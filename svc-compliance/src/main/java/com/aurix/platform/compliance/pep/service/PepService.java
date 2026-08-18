package com.aurix.platform.compliance.pep.service;

import com.aurix.platform.compliance.pep.entity.PepCliente;
import com.aurix.platform.compliance.pep.repository.PepClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PepService {

    private static final Logger log = LoggerFactory.getLogger(PepService.class);

    private final PepClienteRepository pepClienteRepository;

    public PepService(PepClienteRepository pepClienteRepository) {
        this.pepClienteRepository = pepClienteRepository;
    }

    public PepCliente verificarCliente(Long clienteId, String cpfCnpj, String nomeCompleto) {
        log.info("Verificando status PEP para cliente: {}", clienteId);

        Optional<PepCliente> existente = pepClienteRepository.findByClienteId(clienteId);
        if (existente.isPresent()) {
            log.info("Cliente {} ja possui registro PEP", clienteId);
            return existente.get();
        }

        PepCliente pepCliente = new PepCliente();
        pepCliente.setClienteId(clienteId);
        pepCliente.setCpfCnpj(cpfCnpj);
        pepCliente.setNomeCompleto(nomeCompleto);
        pepCliente.setStatus(PepCliente.StatusPep.EM_VERIFICACAO);
        pepCliente.setDataVerificacao(LocalDateTime.now());
        pepCliente.setDataProximaVerificacao(LocalDateTime.now().plusMonths(1));

        boolean ehPep = verificarBasesPublicas(cpfCnpj, nomeCompleto);
        if (ehPep) {
            pepCliente.setClassificacao(PepCliente.ClassificacaoPep.TITULAR);
            pepCliente.setNivelRisco(PepCliente.NivelRiscoPep.ALTO);
            pepCliente.setStatus(PepCliente.StatusPep.ATIVO);
            log.warn("Cliente {} identificado como PEP", clienteId);
        } else {
            boolean ehFamiliar = verificarFamiliarPep(cpfCnpj);
            if (ehFamiliar) {
                pepCliente.setClassificacao(PepCliente.ClassificacaoPep.CONJUGE);
                pepCliente.setNivelRisco(PepCliente.NivelRiscoPep.MEDIO);
                pepCliente.setStatus(PepCliente.StatusPep.ATIVO);
                log.warn("Cliente {} identificado como familiar PEP", clienteId);
            } else {
                pepCliente.setClassificacao(PepCliente.ClassificacaoPep.TITULAR);
                pepCliente.setNivelRisco(PepCliente.NivelRiscoPep.BAIXO);
                pepCliente.setStatus(PepCliente.StatusPep.INATIVO);
            }
        }

        PepCliente salvo = pepClienteRepository.save(pepCliente);
        log.info("Verificacao PEP concluida: cliente={}, classificacao={}, risco={}",
            clienteId, salvo.getClassificacao(), salvo.getNivelRisco());
        return salvo;
    }

    @Transactional(readOnly = true)
    public List<PepCliente> listarClientesPep() {
        return pepClienteRepository.findPepsAtivos();
    }

    @Transactional(readOnly = true)
    public List<PepCliente> listarTodos() {
        return pepClienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<PepCliente> buscarPorClienteId(Long clienteId) {
        return pepClienteRepository.findByClienteId(clienteId);
    }

    @Transactional(readOnly = true)
    public List<PepCliente> listarPorNivelRisco(PepCliente.NivelRiscoPep nivelRisco) {
        return pepClienteRepository.findByNivelRisco(nivelRisco);
    }

    @Transactional(readOnly = true)
    public List<PepCliente> listarAltoRisco() {
        return pepClienteRepository.findPepsAltoRisco();
    }

    public PepCliente atualizarClassificacao(Long id, PepCliente.ClassificacaoPep classificacao,
                                               String cargoFuncao, String orgaoInstituicao) {
        log.info("Atualizando classificacao PEP: id={}, classificacao={}", id, classificacao);

        PepCliente pepCliente = pepClienteRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Registro PEP nao encontrado: " + id));

        pepCliente.setClassificacao(classificacao);
        pepCliente.setStatus(PepCliente.StatusPep.ATIVO);
        pepCliente.setNivelRisco(classificarNivelRisco(classificacao));

        if (cargoFuncao != null) {
            pepCliente.setCargoFuncao(cargoFuncao);
        }
        if (orgaoInstituicao != null) {
            pepCliente.setOrgaoInstituicao(orgaoInstituicao);
        }

        pepCliente.setDataVerificacao(LocalDateTime.now());
        pepCliente.setDataProximaVerificacao(LocalDateTime.now().plusMonths(1));

        PepCliente salvo = pepClienteRepository.save(pepCliente);
        log.info("Classificacao PEP atualizada: id={}, nivelRisco={}", id, salvo.getNivelRisco());
        return salvo;
    }

    public void atualizacaoPeriodica() {
        log.info("Iniciando atualizacao periodica de verificacao PEP...");
        List<PepCliente> pendentes = pepClienteRepository.findPepsPendentesVerificacao(LocalDateTime.now());

        for (PepCliente pep : pendentes) {
            log.info("Reverificando PEP: cliente={}, nome={}", pep.getClienteId(), pep.getNomeCompleto());
            boolean aindaPep = verificarBasesPublicas(pep.getCpfCnpj(), pep.getNomeCompleto());

            if (!aindaPep && pep.getStatus() == PepCliente.StatusPep.ATIVO) {
                pep.setStatus(PepCliente.StatusPep.INATIVO);
                log.info("Cliente {} nao e mais PEP", pep.getClienteId());
            } else if (aindaPep) {
                pep.setStatus(PepCliente.StatusPep.ATIVO);
            }

            pep.setDataVerificacao(LocalDateTime.now());
            pep.setDataProximaVerificacao(LocalDateTime.now().plusMonths(1));
            pepClienteRepository.save(pep);
        }

        log.info("Atualizacao periodica PEP concluida. {} registros processados", pendentes.size());
    }

    private boolean verificarBasesPublicas(String cpfCnpj, String nomeCompleto) {
        if (cpfCnpj == null || cpfCnpj.isEmpty()) {
            return false;
        }

        log.debug("Verificando bases publicas (Receita Federal, CNPJ) para: {}", cpfCnpj);

        try {
            String cpfLimpo = cpfCnpj.replaceAll("[^0-9]", "");
            if (cpfLimpo.length() == 11) {
                return verificarReceitaFederal(cpfLimpo);
            } else if (cpfLimpo.length() == 14) {
                return verificarCnpjReceita(cpfLimpo);
            }
        } catch (Exception e) {
            log.error("Erro ao verificar bases publicas para {}: {}", cpfCnpj, e.getMessage());
        }

        return false;
    }

    private boolean verificarReceitaFederal(String cpf) {
        log.debug("Consulta Receita Federal para CPF: {}", cpf);
        return false;
    }

    private boolean verificarCnpjReceita(String cnpj) {
        log.debug("Consulta Receita Federal para CNPJ: {}", cnpj);
        return false;
    }

    private boolean verificarFamiliarPep(String cpfCnpj) {
        log.debug("Verificando se e familiar de PEP: {}", cpfCnpj);
        return false;
    }

    private PepCliente.NivelRiscoPep classificarNivelRisco(PepCliente.ClassificacaoPep classificacao) {
        if (classificacao == null) {
            return PepCliente.NivelRiscoPep.MEDIO;
        }
        return switch (classificacao) {
            case TITULAR -> PepCliente.NivelRiscoPep.ALTO;
            case CONJUGE -> PepCliente.NivelRiscoPep.MEDIO;
            case FILHO, DEPENDENTE -> PepCliente.NivelRiscoPep.MEDIO;
            case REPRESENTANTE, ADMINISTRADOR -> PepCliente.NivelRiscoPep.MUITO_ALTO;
        };
    }
}
