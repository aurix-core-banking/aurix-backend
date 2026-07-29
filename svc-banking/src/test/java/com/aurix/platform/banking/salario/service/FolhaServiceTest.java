package com.aurix.platform.banking.salario.service;

import com.aurix.platform.banking.BankingApplication;
import com.aurix.platform.banking.salario.config.TestSalarioConfig;
import com.aurix.platform.banking.salario.dto.CreditoDiretoRequest;
import com.aurix.platform.banking.salario.entity.ContaSalario;
import com.aurix.platform.banking.salario.entity.FolhaPagamento;
import com.aurix.platform.banking.salario.entity.ItemFolhaPagamento;
import com.aurix.platform.banking.salario.repository.ContaSalarioRepository;
import com.aurix.platform.banking.salario.repository.FolhaPagamentoRepository;
import com.aurix.platform.banking.salario.repository.ItemFolhaPagamentoRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = BankingApplication.class)
@ActiveProfiles("test")
@Import(TestSalarioConfig.class)
class FolhaServiceTest {

    @Autowired
    private FolhaPagamentoRepository folhaRepository;

    @Autowired
    private ItemFolhaPagamentoRepository itemRepository;

    @Autowired
    private ContaSalarioRepository contaSalarioRepository;

    @Autowired
    private FolhaService folhaService;

    @BeforeEach
    void setUp() {
        TenantContext.setTenantId("default");
    }

    @Test
    void deveListarFolhasPendentes() {
        FolhaPagamento folha = new FolhaPagamento(1L, "teste.txt", 1,
            new BigDecimal("1000.00"), LocalDate.now());
        folha.setTenantId("default");
        folha.setStatus(FolhaPagamento.StatusFolha.VALIDADO);
        folhaRepository.save(folha);

        var pendentes = folhaService.listarFolhasPendentes();

        assertThat(pendentes).isNotEmpty();
        assertThat(pendentes.get(0).getArquivoNome()).isEqualTo("teste.txt");
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaPendentes() {
        var pendentes = folhaService.listarFolhasPendentes();
        assertThat(pendentes).isEmpty();
    }

    @Test
    void deveLancarExcecaoQuandoCreditoDiretoSemConta() {
        CreditoDiretoRequest request = new CreditoDiretoRequest();
        request.setEmpresaId(999L);
        request.setCpfFuncionario("00000000000");
        request.setValorLiquido(new BigDecimal("1000.00"));

        assertThatThrownBy(() -> folhaService.creditarDireto(request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("nao encontrada");
    }

    @Test
    void deveSalvarItensDaFolha() {
        FolhaPagamento folha = new FolhaPagamento(1L, "folha.txt", 2,
            new BigDecimal("5000.00"), LocalDate.now());
        folha.setTenantId("default");
        FolhaPagamento salva = folhaRepository.save(folha);

        ItemFolhaPagamento item = new ItemFolhaPagamento(
            salva.getId(), null, "12345678901", new BigDecimal("2500.00"));
        item.setTenantId("default");
        ItemFolhaPagamento salvo = itemRepository.save(item);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getStatus()).isEqualTo(ItemFolhaPagamento.StatusItem.PENDENTE);
    }
}
