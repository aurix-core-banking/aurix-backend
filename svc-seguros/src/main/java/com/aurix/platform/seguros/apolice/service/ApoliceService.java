package com.aurix.platform.seguros.apolice.service;

import com.aurix.platform.seguros.apolice.dto.ApoliceRequest;
import com.aurix.platform.seguros.apolice.dto.ApoliceResponse;
import com.aurix.platform.seguros.apolice.entity.Apolice;
import com.aurix.platform.seguros.apolice.entity.StatusApolice;
import com.aurix.platform.seguros.apolice.repository.ApoliceRepository;
import com.aurix.platform.seguros.produto.entity.TipoCobertura;
import com.aurix.platform.seguros.produto.repository.ProdutoSeguroRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApoliceService {

    private static final Logger log = LoggerFactory.getLogger(ApoliceService.class);

    private final ApoliceRepository apoliceRepository;
    private final ProdutoSeguroRepository produtoRepository;
    private final com.aurix.platform.seguros.produto.service.ProdutoSeguroService produtoSeguroService;

    public ApoliceService(ApoliceRepository apoliceRepository,
                          ProdutoSeguroRepository produtoRepository,
                          com.aurix.platform.seguros.produto.service.ProdutoSeguroService produtoSeguroService) {
        this.apoliceRepository = apoliceRepository;
        this.produtoRepository = produtoRepository;
        this.produtoSeguroService = produtoSeguroService;
    }

    @Transactional
    public ApoliceResponse contratar(ApoliceRequest request) {
        var produto = produtoRepository.findById(request.produtoId())
            .orElseThrow(() -> new IllegalArgumentException("Produto de seguro não encontrado: " + request.produtoId()));

        if (!produto.getAtivo()) {
            throw new IllegalStateException("Produto de seguro não está ativo: " + request.produtoId());
        }

        var premio = produtoSeguroService.calcularPremio(
            request.valorSegurado(), produto.getTaxaBase(),
            request.idadeSegurado(), request.uf(), request.sexo(), request.profissao()
        );

        if (premio.compareTo(produto.getPremioMinimo()) < 0) {
            premio = produto.getPremioMinimo();
        }

        var premioMensal = premio.divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
        var dataInicio = LocalDate.now();
        var dataFim = dataInicio.plusYears(1);

        var numeroApolice = gerarNumeroApolice();

        var apolice = new Apolice(
            request.tenantId(), request.clienteId(), request.produtoId(),
            produto.getTipo().name(), request.cobertura().name(),
            request.valorSegurado(), premio, premioMensal,
            dataInicio, dataFim, StatusApolice.EMITIDA,
            request.idadeSegurado(), request.uf(), request.sexo(),
            request.profissao(), numeroApolice
        );
        apolice.setRenovacaoAutomatica(request.renovacaoAutomatica());

        apolice = apoliceRepository.save(apolice);

        log.info("Apólice contratada: id={}, cliente={}, numero={}, premio={}",
            apolice.getId(), request.clienteId(), numeroApolice, premio);

        return toResponse(apolice);
    }

    @Transactional(readOnly = true)
    public ApoliceResponse buscarPorId(Long id) {
        var entity = apoliceRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Apólice não encontrada: " + id));
        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<ApoliceResponse> listarPorCliente(Long clienteId) {
        return apoliceRepository.findByClienteId(clienteId).stream()
            .map(this::toResponse)
            .toList();
    }

    @Transactional
    public ApoliceResponse cancelar(Long id) {
        var apolice = apoliceRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Apólice não encontrada: " + id));

        if (apolice.getStatus() == StatusApolice.CANCELADA) {
            throw new IllegalStateException("Apólice já está cancelada: " + id);
        }

        if (apolice.getStatus() == StatusApolice.VENCIDA) {
            throw new IllegalStateException("Apólice já está vencida: " + id);
        }

        var dataCancelamento = LocalDate.now();
        var totalDias = java.time.temporal.ChronoUnit.DAYS.between(apolice.getDataInicio(), apolice.getDataFim());
        var diasRestantes = java.time.temporal.ChronoUnit.DAYS.between(dataCancelamento, apolice.getDataFim());

        BigDecimal valorRestituido = BigDecimal.ZERO;
        if (diasRestantes > 0 && totalDias > 0) {
            var proporcao = BigDecimal.valueOf(diasRestantes)
                .divide(BigDecimal.valueOf(totalDias), 6, RoundingMode.HALF_UP);
            valorRestituido = apolice.getPremio().multiply(proporcao).setScale(2, RoundingMode.HALF_UP);
        }

        apolice.setStatus(StatusApolice.CANCELADA);
        apolice.setDataCancelamento(dataCancelamento);
        apolice.setValorRestituido(valorRestituido);

        apolice = apoliceRepository.save(apolice);

        log.info("Apólice cancelada: id={}, restituido={}", apolice.getId(), valorRestituido);

        return toResponse(apolice);
    }

    @Transactional
    public void renovar(Apolice apolice) {
        var novaDataInicio = apolice.getDataFim();
        var novaDataFim = novaDataInicio.plusYears(1);
        var numeroApolice = gerarNumeroApolice();

        var novaApolice = new Apolice(
            apolice.getTenantId(), apolice.getClienteId(), apolice.getProdutoId(),
            apolice.getProdutoTipo(), apolice.getCobertura(), apolice.getValorSegurado(),
            apolice.getPremio(), apolice.getPremioMensal(),
            novaDataInicio, novaDataFim, StatusApolice.EMITIDA,
            apolice.getIdadeSegurado(), apolice.getUf(), apolice.getSexo(),
            apolice.getProfissao(), numeroApolice
        );
        novaApolice.setRenovacaoAutomatica(apolice.getRenovacaoAutomatica());

        apoliceRepository.save(novaApolice);

        log.info("Apólice renovada: anteriorId={}, novaId={}, numero={}",
            apolice.getId(), novaApolice.getId(), numeroApolice);
    }

    private String gerarNumeroApolice() {
        var data = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        var random = ThreadLocalRandom.current().nextInt(10000, 99999);
        return "SEG-" + data + "-" + random;
    }

    private ApoliceResponse toResponse(Apolice a) {
        return new ApoliceResponse(
            a.getId(), a.getTenantId(), a.getClienteId(), a.getProdutoId(),
            a.getProdutoTipo(), a.getCobertura(), a.getValorSegurado(),
            a.getPremio(), a.getPremioMensal(), a.getDataInicio(), a.getDataFim(),
            a.getDataCancelamento(), a.getValorRestituido(),
            a.getStatus().name(), a.getRenovacaoAutomatica(),
            a.getIdadeSegurado(), a.getUf(), a.getSexo(), a.getProfissao(),
            a.getNumeroApolice(), a.getDataCriacao()
        );
    }
}
