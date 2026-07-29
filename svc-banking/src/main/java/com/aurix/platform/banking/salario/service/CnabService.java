package com.aurix.platform.banking.salario.service;

import com.aurix.platform.banking.salario.client.CnabParser;
import com.aurix.platform.banking.salario.entity.ContaSalario;
import com.aurix.platform.banking.salario.entity.FolhaPagamento;
import com.aurix.platform.banking.salario.entity.ItemFolhaPagamento;
import com.aurix.platform.banking.salario.repository.ContaSalarioRepository;
import com.aurix.platform.banking.salario.repository.ConvenioEmpresaRepository;
import com.aurix.platform.banking.salario.repository.FolhaPagamentoRepository;
import com.aurix.platform.banking.salario.repository.ItemFolhaPagamentoRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.InputStream;
import java.time.LocalDate;

@Service
@Transactional
public class CnabService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CnabService.class);

    private final CnabParser cnabParser;
    private final FolhaPagamentoRepository folhaRepository;
    private final ItemFolhaPagamentoRepository itemRepository;
    private final ConvenioEmpresaRepository convenioRepository;
    private final ContaSalarioRepository contaSalarioRepository;

    public CnabService(CnabParser cnabParser,
                       FolhaPagamentoRepository folhaRepository,
                       ItemFolhaPagamentoRepository itemRepository,
                       ConvenioEmpresaRepository convenioRepository,
                       ContaSalarioRepository contaSalarioRepository) {
        this.cnabParser = cnabParser;
        this.folhaRepository = folhaRepository;
        this.itemRepository = itemRepository;
        this.convenioRepository = convenioRepository;
        this.contaSalarioRepository = contaSalarioRepository;
    }

    public FolhaPagamento processarUpload(String arquivoNome, InputStream inputStream) {
        log.info("Processando upload CNAB: {}", arquivoNome);

        try {
            CnabParser.Resultado resultado = cnabParser.parse(arquivoNome, inputStream);

            String tenantId = TenantContext.getTenantId();

            Long empresaId = null;
            if (resultado.nomeEmpresa() != null && !resultado.nomeEmpresa().isBlank()) {
                empresaId = convenioRepository.findByTenantIdAndRazaoSocial(
                    tenantId, resultado.nomeEmpresa()
                ).map(emp -> emp.getId()).orElse(null);
            }

            FolhaPagamento folha = new FolhaPagamento(
                empresaId,
                arquivoNome,
                resultado.totalFuncionarios(),
                resultado.valorTotal(),
                resultado.dataGeracao()
            );
            folha.setTenantId(tenantId);
            folha.setStatus(FolhaPagamento.StatusFolha.VALIDADO);

            FolhaPagamento salva = folhaRepository.save(folha);

            for (CnabParser.Detalhe detalhe : resultado.detalhes()) {
                Long contaSalarioId = null;
                if (empresaId != null) {
                    var contaOpt = contaSalarioRepository
                        .findByTenantIdAndEmpresaIdAndMatriculaFuncionario(
                            tenantId, empresaId, detalhe.matricula());
                    contaSalarioId = contaOpt.map(c -> c.getId()).orElse(null);
                    contaOpt.ifPresent(conta -> {
                        if (conta.getCpfFuncionario() == null || conta.getCpfFuncionario().isBlank()) {
                            conta.setCpfFuncionario(detalhe.cpf());
                            contaSalarioRepository.save(conta);
                        }
                    });
                }

                ItemFolhaPagamento item = new ItemFolhaPagamento(
                    salva.getId(), contaSalarioId, detalhe.cpf(), detalhe.valor()
                );
                item.setTenantId(tenantId);
                itemRepository.save(item);
            }

            log.info("CNAB processado: {} funcionarios, total R$ {}", resultado.totalFuncionarios(), resultado.valorTotal());
            return salva;

        } catch (Exception e) {
            log.error("Erro ao processar CNAB: {}", e.getMessage());

            FolhaPagamento folha = new FolhaPagamento(
                null, arquivoNome, 0, java.math.BigDecimal.ZERO, LocalDate.now()
            );
            folha.setTenantId(TenantContext.getTenantId());
            folha.setStatus(FolhaPagamento.StatusFolha.ERRO_ESTRUTURA);
            return folhaRepository.save(folha);
        }
    }
}
