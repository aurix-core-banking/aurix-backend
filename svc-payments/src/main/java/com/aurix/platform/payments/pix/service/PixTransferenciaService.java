package com.aurix.platform.payments.pix.service;

import com.aurix.platform.payments.pix.client.PixBacenClient;
import com.aurix.platform.payments.pix.client.dto.SpiResult;
import com.aurix.platform.payments.pix.client.dto.TransacaoSPI;
import com.aurix.platform.payments.pix.repository.ContaRepository;
import com.aurix.platform.payments.pix.repository.PixChaveRepository;
import com.aurix.platform.payments.pix.repository.PixTransferenciaRepository;
import com.aurix.platform.shared.dto.PixTransferenciaDTO;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.PixChave;
import com.aurix.platform.shared.entity.PixTransferencia;
import com.aurix.platform.shared.event.EventPublisher;
import com.aurix.platform.shared.event.TransacaoEvent;
import com.aurix.platform.shared.exception.SaldoInsuficienteException;
import com.aurix.platform.shared.tenant.TenantContext;
import com.aurix.platform.shared.util.TransacaoUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Serviço para gestão de transferências PIX
 */
@Service
@Transactional
public class PixTransferenciaService {
        @java.lang.SuppressWarnings("all")
        private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PixTransferenciaService.class);
        private final PixTransferenciaRepository pixTransferenciaRepository;
        private final ContaRepository contaRepository;
        private final PixChaveRepository pixChaveRepository;
        private final EventPublisher eventPublisher;
        private final PixBacenClient pixBacenClient;
        private final String ispb;

        /**
         * Cria uma nova transferência PIX
         */
        public PixTransferenciaDTO criarTransferenciaPix(PixTransferenciaDTO pixTransferenciaDTO) {
                log.info("Criando transferência PIX para chave: {}", pixTransferenciaDTO.getChavePixDestino());
                // Buscar conta origem
                var contaOrigem = contaRepository.findById(pixTransferenciaDTO.getContaOrigemId()).orElseThrow(() -> new IllegalArgumentException("Conta origem não encontrada: " + pixTransferenciaDTO.getContaOrigemId()));
                // Gerar código PIX único
                String codigoPix = TransacaoUtil.gerarCodigoPix();
                // Criar entidade
                PixTransferencia pixTransferencia = new PixTransferencia();
                pixTransferencia.setCodigoPix(codigoPix);
                pixTransferencia.setContaOrigem(contaOrigem);
                pixTransferencia.setChavePixDestino(pixTransferenciaDTO.getChavePixDestino());
                pixTransferencia.setNomeDestinatario(pixTransferenciaDTO.getNomeDestinatario());
                pixTransferencia.setValor(pixTransferenciaDTO.getValor());
                pixTransferencia.setDescricao(pixTransferenciaDTO.getDescricao());
                pixTransferencia.setTipoChave(pixTransferenciaDTO.getTipoChave());
                pixTransferencia.setStatus(PixTransferencia.StatusPix.PENDENTE);
                pixTransferencia.setDadosAdicionais(pixTransferenciaDTO.getDadosAdicionais());
                // Salvar
                PixTransferencia transferenciaSalva = pixTransferenciaRepository.save(pixTransferencia);
                log.info("Transferência PIX criada com código: {}", transferenciaSalva.getCodigoPix());
                return converterParaDTO(transferenciaSalva);
        }

        /**
         * Busca transferência por ID
         */
        @Transactional(readOnly = true)
        public PixTransferenciaDTO buscarTransferenciaPorId(Long id) {
                log.info("Buscando transferência PIX por ID: {}", id);
                PixTransferencia pixTransferencia = pixTransferenciaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transferência PIX não encontrada"));
                return converterParaDTO(pixTransferencia);
        }

        /**
         * Busca transferência por código PIX
         */
        @Transactional(readOnly = true)
        public PixTransferenciaDTO buscarTransferenciaPorCodigo(String codigoPix) {
                log.info("Buscando transferência PIX por código: {}", codigoPix);
                PixTransferencia pixTransferencia = pixTransferenciaRepository.findByCodigoPix(codigoPix).orElseThrow(() -> new IllegalArgumentException("Transferência PIX não encontrada"));
                return converterParaDTO(pixTransferencia);
        }

        /**
         * Lista transferências por conta
         */
        @Transactional(readOnly = true)
        public List<PixTransferenciaDTO> listarTransferenciasPorConta(Long contaId) {
                log.info("Listando transferências PIX da conta ID: {}", contaId);
                return pixTransferenciaRepository.findByContaOrigemId(contaId).stream().map(this::converterParaDTO).collect(Collectors.toList());
        }

        /**
         * Lista transferências por período
         */
        @Transactional(readOnly = true)
        public List<PixTransferenciaDTO> listarTransferenciasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
                log.info("Listando transferências PIX do período: {} a {}", inicio, fim);
                return pixTransferenciaRepository.findByPeriodo(inicio, fim).stream().map(this::converterParaDTO).collect(Collectors.toList());
        }

        /**
         * Lista transferências por conta e período
         */
        @Transactional(readOnly = true)
        public List<PixTransferenciaDTO> listarTransferenciasPorContaAndPeriodo(Long contaId, LocalDateTime inicio, LocalDateTime fim) {
                log.info("Listando transferências PIX da conta {} do período: {} a {}", contaId, inicio, fim);
                return pixTransferenciaRepository.findByContaAndPeriodo(contaId, inicio, fim).stream().map(this::converterParaDTO).collect(Collectors.toList());
        }

        /**
         * Processa transferência PIX: debita a conta origem de forma atômica e,
         * se a chave de destino pertencer a uma conta deste banco, credita o
         * destinatário na mesma transação (ver ADR-0002). Se a chave de destino
         * não for local, o crédito ocorre na instituição destino via SPI/BACEN
         * (fora do escopo deste módulo).
         */
        public void processarTransferencia(Long id) {
                log.info("Processando transferência PIX ID: {}", id);
                PixTransferencia pixTransferencia = pixTransferenciaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transferência PIX não encontrada"));
                if (pixTransferencia.getStatus() != PixTransferencia.StatusPix.PENDENTE) {
                        throw new IllegalStateException("Transferência não está pendente");
                }
                Long contaOrigemId = pixTransferencia.getContaOrigem().getId();
                String tenantId = TenantContext.getTenantId();
                int debitado = contaRepository.debitarSaldoAtomico(tenantId, contaOrigemId, pixTransferencia.getValor());
                if (debitado == 0) {
                        // 0 linhas afetadas pode significar saldo insuficiente OU que a conta
                        // não existe para este tenant (ex.: TenantContext não foi configurado
                        // igual ao tenantId gravado na conta) — diferenciar para não mascarar
                        // um erro de configuração como se fosse saldo insuficiente.
                        boolean contaExiste = contaRepository.findById(contaOrigemId)
                                        .filter(c -> tenantId.equals(c.getTenantId()))
                                        .isPresent();
                        pixTransferencia.setStatus(PixTransferencia.StatusPix.FALHADA);
                        pixTransferencia.setDataProcessamento(LocalDateTime.now());
                        if (!contaExiste) {
                                pixTransferencia.setCodigoRetorno("01");
                                pixTransferencia.setMensagemRetorno("Conta de origem não encontrada para o tenant atual");
                                pixTransferenciaRepository.save(pixTransferencia);
                                throw new IllegalArgumentException(
                                                "Conta origem não encontrada para o tenant '" + tenantId + "': " + contaOrigemId);
                        }
                        pixTransferencia.setCodigoRetorno("99");
                        pixTransferencia.setMensagemRetorno("Saldo insuficiente na conta de origem");
                        pixTransferenciaRepository.save(pixTransferencia);
                        throw new SaldoInsuficienteException(contaOrigemId, pixTransferencia.getValor());
                }
                String endToEndId = gerarEndToEndId();
                pixTransferencia.setCodigoPix(endToEndId);

                TransacaoSPI transacaoSPI = new TransacaoSPI();
                transacaoSPI.setEndToEndId(endToEndId);
                transacaoSPI.setIspbOrigem(ispb);
                transacaoSPI.setIspbDestino("");
                transacaoSPI.setValor(pixTransferencia.getValor());
                transacaoSPI.setChavePixDestino(pixTransferencia.getChavePixDestino());

                SpiResult resultado;
                try {
                        resultado = pixBacenClient.enviarPix(transacaoSPI);
                } catch (Exception e) {
                        log.error("Falha ao enviar PIX para SPI: {}", e.getMessage());
                        estornarDebito(pixTransferencia, tenantId, contaOrigemId);
                        pixTransferencia.setStatus(PixTransferencia.StatusPix.FALHADA);
                        pixTransferencia.setCodigoRetorno("98");
                        pixTransferencia.setMensagemRetorno("Falha na comunicação com SPI: " + e.getMessage());
                        pixTransferencia.setDataProcessamento(LocalDateTime.now());
                        pixTransferenciaRepository.save(pixTransferencia);
                        throw new IllegalStateException("Falha na comunicação com SPI", e);
                }

                if (!resultado.isSucesso()) {
                        log.warn("PIX rejeitado pelo SPI: {}", resultado.getMensagem());
                        estornarDebito(pixTransferencia, tenantId, contaOrigemId);
                        pixTransferencia.setStatus(PixTransferencia.StatusPix.FALHADA);
                        pixTransferencia.setCodigoRetorno("99");
                        pixTransferencia.setMensagemRetorno("SPI rejeitou: " + resultado.getMensagem());
                        pixTransferencia.setDataProcessamento(LocalDateTime.now());
                        pixTransferenciaRepository.save(pixTransferencia);
                        throw new IllegalStateException("PIX rejeitado pelo SPI: " + resultado.getMensagem());
                }

                // SPI liquidou — atualiza com o endToEndId retornado pelo BACEN
                if (resultado.getEndToEndId() != null && !resultado.getEndToEndId().isBlank()) {
                        pixTransferencia.setCodigoPix(resultado.getEndToEndId());
                }

                Optional<PixChave> chaveDestino = pixChaveRepository.findChaveAtivaByChavePix(pixTransferencia.getChavePixDestino());
                if (chaveDestino.isPresent()) {
                        Conta contaDestino = chaveDestino.get().getConta();
                        contaRepository.creditarSaldoAtomico(tenantId, contaDestino.getId(), pixTransferencia.getValor());
                        log.info("PIX creditado localmente na conta destino ID: {}", contaDestino.getId());
                } else {
                        log.info("PIX liquidado via SPI — chave destino externa: {}", pixTransferencia.getChavePixDestino());
                }
                pixTransferencia.setStatus(PixTransferencia.StatusPix.PROCESSADA);
                pixTransferencia.setDataProcessamento(LocalDateTime.now());
                pixTransferencia.setCodigoRetorno("00");
                pixTransferencia.setMensagemRetorno("Transferência processada com sucesso via SPI");
                pixTransferenciaRepository.save(pixTransferencia);
                try {
                        String contaId = pixTransferencia.getContaOrigem() != null ? String.valueOf(pixTransferencia.getContaOrigem().getId()) : null;
                        String clienteId = (pixTransferencia.getContaOrigem() != null && pixTransferencia.getContaOrigem().getCliente() != null) ? String.valueOf(pixTransferencia.getContaOrigem().getCliente().getId()) : null;
                        eventPublisher.publicarTransacaoRealizada(TransacaoEvent.transacaoRealizada(String.valueOf(pixTransferencia.getId()), contaId, clienteId, pixTransferencia.getValor(), "PIX", pixTransferencia.getDescricao()));
                } catch (Exception e) {
                        log.warn("Falha ao publicar evento transacao-realizada (PIX): {}", e.getMessage());
                }
                log.info("Transferência PIX processada com sucesso");
        }

        /**
         * Cancela transferência PIX
         */
        public void cancelarTransferencia(Long id) {
                log.info("Cancelando transferência PIX ID: {}", id);
                PixTransferencia pixTransferencia = pixTransferenciaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transferência PIX não encontrada"));
                if (pixTransferencia.getStatus() != PixTransferencia.StatusPix.PENDENTE) {
                        throw new IllegalStateException("Transferência não pode ser cancelada");
                }
                pixTransferencia.setStatus(PixTransferencia.StatusPix.CANCELADA);
                pixTransferencia.setDataProcessamento(LocalDateTime.now());
                pixTransferencia.setCodigoRetorno("99");
                pixTransferencia.setMensagemRetorno("Transferência cancelada");
                pixTransferenciaRepository.save(pixTransferencia);
                log.info("Transferência PIX cancelada com sucesso");
        }

        private void estornarDebito(PixTransferencia transferencia, String tenantId, Long contaOrigemId) {
                int estornado = contaRepository.creditarSaldoAtomico(tenantId, contaOrigemId, transferencia.getValor());
                if (estornado == 0) {
                        log.error("CRÍTICO: estorno falhou para transferência ID {} — inconsistência contábil", transferencia.getId());
                }
        }

        private String gerarEndToEndId() {
                return "E" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                        + UUID.randomUUID().toString().replace("-", "").substring(0, 20).toUpperCase();
        }

        /**
         * Converte entidade para DTO
         */
        private PixTransferenciaDTO converterParaDTO(PixTransferencia pixTransferencia) {
                PixTransferenciaDTO dto = new PixTransferenciaDTO();
                dto.setId(pixTransferencia.getId());
                dto.setCodigoPix(pixTransferencia.getCodigoPix());
                dto.setContaOrigemId(pixTransferencia.getContaOrigem() != null ? pixTransferencia.getContaOrigem().getId() : null);
                dto.setContaOrigemNumero(pixTransferencia.getContaOrigem() != null ? pixTransferencia.getContaOrigem().getNumeroConta() : null);
                dto.setChavePixDestino(pixTransferencia.getChavePixDestino());
                dto.setNomeDestinatario(pixTransferencia.getNomeDestinatario());
                dto.setValor(pixTransferencia.getValor());
                dto.setDescricao(pixTransferencia.getDescricao());
                dto.setStatus(pixTransferencia.getStatus());
                dto.setTipoChave(pixTransferencia.getTipoChave());
                dto.setInstituicaoDestino(pixTransferencia.getInstituicaoDestino());
                dto.setAgenciaDestino(pixTransferencia.getAgenciaDestino());
                dto.setContaDestino(pixTransferencia.getContaDestino());
                dto.setDataTransferencia(pixTransferencia.getDataTransferencia());
                dto.setDataProcessamento(pixTransferencia.getDataProcessamento());
                dto.setCodigoRetorno(pixTransferencia.getCodigoRetorno());
                dto.setMensagemRetorno(pixTransferencia.getMensagemRetorno());
                dto.setDadosAdicionais(pixTransferencia.getDadosAdicionais());
                dto.setDataCriacao(pixTransferencia.getDataCriacao() != null ? pixTransferencia.getDataCriacao().toString() : null);
                dto.setDataAtualizacao(pixTransferencia.getDataAtualizacao() != null ? pixTransferencia.getDataAtualizacao().toString() : null);
                return dto;
        }

        @java.lang.SuppressWarnings("all")
        public PixTransferenciaService(final PixTransferenciaRepository pixTransferenciaRepository, final ContaRepository contaRepository, final PixChaveRepository pixChaveRepository, final EventPublisher eventPublisher, final PixBacenClient pixBacenClient, @Value("${aurix.pix.ispb:}") final String ispb) {
                this.pixTransferenciaRepository = pixTransferenciaRepository;
                this.contaRepository = contaRepository;
                this.pixChaveRepository = pixChaveRepository;
                this.eventPublisher = eventPublisher;
                this.pixBacenClient = pixBacenClient;
                this.ispb = ispb;
        }
}
