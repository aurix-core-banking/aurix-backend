package com.aurix.platform.banking.cnab.service;

import com.aurix.platform.banking.cnab.dto.CnabRemessaRequest;
import com.aurix.platform.banking.cnab.dto.CnabRemessaResponse;
import com.aurix.platform.banking.cnab.entity.CnabRemessa;
import com.aurix.platform.banking.cnab.entity.CnabRemessa.StatusRemessa;
import com.aurix.platform.banking.cnab.entity.CnabRemessa.TipoCnab;
import com.aurix.platform.banking.cnab.entity.CnabRetorno;
import com.aurix.platform.banking.cnab.repository.CnabRemessaRepository;
import com.aurix.platform.banking.cnab.repository.CnabRetornoRepository;
import com.aurix.platform.banking.ted.entity.TransferenciaTed;
import com.aurix.platform.banking.ted.repository.TransferenciaTedRepository;
import com.aurix.platform.banking.boleto.entity.BoletoRegistrado;
import com.aurix.platform.banking.boleto.repository.BoletoRegistradoRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CnabService {

    private static final Logger log = LoggerFactory.getLogger(CnabService.class);
    private static final DateTimeFormatter FMT_DATA = DateTimeFormatter.ofPattern("ddMMyyyy");
    private static final DateTimeFormatter FMT_HORA = DateTimeFormatter.ofPattern("HHmmss");
    private static final int HEADER_SIZE = 240;
    private static final int DETALHE_SIZE = 240;
    private static final int TRAILER_SIZE = 240;

    private final CnabRemessaRepository remessaRepository;
    private final CnabRetornoRepository retornoRepository;
    private final TransferenciaTedRepository tedRepository;
    private final BoletoRegistradoRepository boletoRepository;

    public CnabService(CnabRemessaRepository remessaRepository,
                       CnabRetornoRepository retornoRepository,
                       TransferenciaTedRepository tedRepository,
                       BoletoRegistradoRepository boletoRepository) {
        this.remessaRepository = remessaRepository;
        this.retornoRepository = retornoRepository;
        this.tedRepository = tedRepository;
        this.boletoRepository = boletoRepository;
    }

    public CnabRemessaResponse gerarRemessa(CnabRemessaRequest request) {
        log.info("Gerando CNAB remessa: tipo={}", request.getTipo());

        String tenantId = TenantContext.getTenantId();
        String conteudoArquivo;
        int totalRegistros = 0;

        switch (request.getTipo()) {
            case PAGAMENTO_TED:
                conteudoArquivo = gerarRemessaPagamentoTed(tenantId, request.getTransferenciaIds());
                totalRegistros = request.getTransferenciaIds() != null ? request.getTransferenciaIds().length : 0;
                break;
            case PAGAMENTO_BOLETO:
                conteudoArquivo = gerarRemessaPagamentoBoleto(tenantId, request.getBoletoIds());
                totalRegistros = request.getBoletoIds() != null ? request.getBoletoIds().length : 0;
                break;
            default:
                conteudoArquivo = gerarRemessaGenerica(tenantId, request.getTipo());
                break;
        }

        String nomeArquivo = gerarNomeArquivo(request.getTipo());

        CnabRemessa remessa = new CnabRemessa();
        remessa.setTenantId(tenantId);
        remessa.setTipo(request.getTipo());
        remessa.setArquivoNome(nomeArquivo);
        remessa.setConteudoArquivo(conteudoArquivo);
        remessa.setTotalRegistros(totalRegistros);
        remessa.setStatus(StatusRemessa.GERADO);
        remessa.setDataGeracao(LocalDateTime.now());

        CnabRemessa salva = remessaRepository.save(remessa);

        log.info("Remessa CNAB gerada: id={}, arquivo={}, registros={}",
            salva.getId(), salva.getArquivoNome(), totalRegistros);

        return converterParaResponse(salva);
    }

    public CnabRemessaResponse processarRetorno(byte[] conteudoArquivo, String nomeArquivo) {
        log.info("Processando retorno CNAB: {}", nomeArquivo);

        String tenantId = TenantContext.getTenantId();
        String conteudo = new String(conteudoArquivo);

        CnabRetorno retorno = new CnabRetorno();
        retorno.setTenantId(tenantId);
        retorno.setArquivoNome(nomeArquivo);
        retorno.setConteudoArquivo(conteudo);
        retorno.setProcessado(false);

        try {
            String[] linhas = conteudo.split("\n");
            int registrosProcessados = 0;
            int registrosComErro = 0;

            for (String linha : linhas) {
                if (linha.length() < 3) {
                    continue;
                }

                String tipoRegistro = linha.substring(0, 1);
                switch (tipoRegistro) {
                    case "1":
                        registrosProcessados++;
                        break;
                    case "9":
                        log.info("Trailer do retorno processado");
                        break;
                    default:
                        log.debug("Tipo de registro desconhecido no retorno: {}", tipoRegistro);
                        break;
                }
            }

            retorno.setTotalRegistros(registrosProcessados);
            retorno.setTotalErros(registrosComErro);
            retorno.setProcessado(true);
            retorno.setDataProcessamento(LocalDateTime.now());

        } catch (Exception e) {
            log.error("Erro ao processar retorno CNAB: {}", e.getMessage());
            retorno.setTotalErros(1);
        }

        retornoRepository.save(retorno);

        log.info("Retorno CNAB processado: registros={}, erros={}",
            retorno.getTotalRegistros(), retorno.getTotalErros());

        CnabRemessaResponse response = new CnabRemessaResponse();
        response.setId(retorno.getId());
        response.setArquivoNome(retorno.getArquivoNome());
        response.setStatus(retorno.getProcessado() ? StatusRemessa.RETURNADO : StatusRemessa.ERRO);
        response.setDataGeracao(retorno.getDataProcessamento());
        return response;
    }

    @Transactional(readOnly = true)
    public List<CnabRemessaResponse> listarRemessas() {
        String tenantId = TenantContext.getTenantId();
        return remessaRepository.findByTenantId(tenantId).stream()
            .map(this::converterParaResponse)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CnabRemessaResponse buscarRemessaPorId(Long id) {
        String tenantId = TenantContext.getTenantId();
        CnabRemessa remessa = remessaRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new IllegalArgumentException("Remessa nao encontrada: " + id));
        return converterParaResponse(remessa);
    }

    private String gerarRemessaPagamentoTed(String tenantId, Long[] transferenciaIds) {
        StringBuilder sb = new StringBuilder();

        sb.append(gerarHeader(tenantId, TipoCnab.PAGAMENTO_TED));

        if (transferenciaIds != null) {
            for (Long tedId : transferenciaIds) {
                TransferenciaTed ted = tedRepository.findById(tedId).orElse(null);
                if (ted != null && ted.getTenantId().equals(tenantId)) {
                    sb.append(gerarDetalheTed(ted));
                }
            }
        }

        sb.append(gerarTrailer(transferenciaIds != null ? transferenciaIds.length : 0));

        return sb.toString();
    }

    private String gerarRemessaPagamentoBoleto(String tenantId, Long[] boletoIds) {
        StringBuilder sb = new StringBuilder();

        sb.append(gerarHeader(tenantId, TipoCnab.PAGAMENTO_BOLETO));

        if (boletoIds != null) {
            for (Long boletoId : boletoIds) {
                BoletoRegistrado boleto = boletoRepository.findById(boletoId).orElse(null);
                if (boleto != null && boleto.getTenantId().equals(tenantId)) {
                    sb.append(gerarDetalheBoleto(boleto));
                }
            }
        }

        sb.append(gerarTrailer(boletoIds != null ? boletoIds.length : 0));

        return sb.toString();
    }

    private String gerarRemessaGenerica(String tenantId, TipoCnab tipo) {
        StringBuilder sb = new StringBuilder();
        sb.append(gerarHeader(tenantId, tipo));
        sb.append(gerarTrailer(0));
        return sb.toString();
    }

    private String gerarHeader(String tenantId, TipoCnab tipo) {
        StringBuilder header = new StringBuilder();
        header.append("0");                                  // Tipo registro (0=header)
        header.append("1");                                  // Codigo operacao (1=inclusao)
        header.append("REMESSA");                            // Literal remessa
        header.append(String.format("%06d", 1));             // Codigo do servico
        header.append(preencher(tenantId, 20));              // Codigo do convenio
        header.append(preencher("AURIX", 25));               // Nome da empresa
        header.append(String.format("%06d", 0));             // Codigo banco
        header.append(preencher("", 10));                    // Brancos
        header.append(LocalDate.now().format(FMT_DATA));     // Data de geracao
        header.append(preencher("", 8));                     // Brancos
        header.append(String.format("%06d", tipo.ordinal())); // Numero sequencial
        header.append(preencher("", HEADER_SIZE - header.length())); // Complemento

        return header.toString() + "\n";
    }

    private String gerarDetalheTed(TransferenciaTed ted) {
        StringBuilder detalhe = new StringBuilder();
        detalhe.append("1");                                        // Tipo registro (1=detalhe)
        detalhe.append("3");                                        // Codigo movimento (3=inclusao)
        detalhe.append(preencher(ted.getContaOrigemNumero(), 20));  // Conta origem
        detalhe.append(preencher(ted.getIspbDestino(), 8));         // ISPB destino
        detalhe.append(preencher(ted.getContaDestinoAgencia(), 4)); // Agencia destino
        detalhe.append(preencher(ted.getContaDestinoConta(), 20));  // Conta destino
        detalhe.append(preencher(ted.getContaDestinoNome(), 30));   // Nome destino
        detalhe.append(preencher(ted.getContaDestinoDocumento(), 14)); // Documento destino
        detalhe.append(String.format("%015d", ted.getValor().movePointRight(2).longValue())); // Valor
        detalhe.append(preencher(ted.getDescricao(), 40));          // Descricao
        detalhe.append(preencher(ted.getCodigoBancoDestino(), 3));  // Banco destino
        detalhe.append(preencher("", DETALHE_SIZE - detalhe.length())); // Complemento

        return detalhe.toString() + "\n";
    }

    private String gerarDetalheBoleto(BoletoRegistrado boleto) {
        StringBuilder detalhe = new StringBuilder();
        detalhe.append("1");                                          // Tipo registro (1=detalhe)
        detalhe.append("3");                                          // Codigo movimento (3=inclusao)
        detalhe.append(preencher(boleto.getNossoNumero(), 20));       // Nosso numero
        detalhe.append(preencher(boleto.getCodigoBarras(), 44));      // Codigo de barras
        detalhe.append(preencher(boleto.getLinhaDigitavel(), 54));    // Linha digitavel
        detalhe.append(preencher(boleto.getContaNumero(), 20));       // Conta
        detalhe.append(String.format("%015d", boleto.getValor().movePointRight(2).longValue())); // Valor
        detalhe.append(boleto.getDataVencimento().format(FMT_DATA));  // Vencimento
        detalhe.append(preencher(boleto.getPagadorNome(), 30));       // Pagador
        detalhe.append(preencher(boleto.getPagadorDocumento(), 14));  // Doc pagador
        detalhe.append(preencher("", DETALHE_SIZE - detalhe.length())); // Complemento

        return detalhe.toString() + "\n";
    }

    private String gerarTrailer(int totalRegistros) {
        StringBuilder trailer = new StringBuilder();
        trailer.append("9");                                          // Tipo registro (9=trailer)
        trailer.append(String.format("%06d", totalRegistros));        // Total registros
        trailer.append(String.format("%015d", 0));                    // Valor total
        trailer.append(preencher("", TRAILER_SIZE - trailer.length())); // Complemento

        return trailer.toString() + "\n";
    }

    private String gerarNomeArquivo(TipoCnab tipo) {
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "CNAB_" + tipo.name() + "_" + dataHora + ".REM";
    }

    private String preencher(String valor, int tamanho) {
        if (valor == null) {
            valor = "";
        }
        if (valor.length() > tamanho) {
            return valor.substring(0, tamanho);
        }
        return String.format("%-" + tamanho + "s", valor);
    }

    private CnabRemessaResponse converterParaResponse(CnabRemessa remessa) {
        CnabRemessaResponse response = new CnabRemessaResponse();
        response.setId(remessa.getId());
        response.setTipo(remessa.getTipo());
        response.setArquivoNome(remessa.getArquivoNome());
        response.setStatus(remessa.getStatus());
        response.setTotalRegistros(remessa.getTotalRegistros());
        response.setDataGeracao(remessa.getDataGeracao());
        response.setDataEnvio(remessa.getDataEnvio());
        return response;
    }
}
