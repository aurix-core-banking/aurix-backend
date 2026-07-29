package com.aurix.platform.customer.onboarding.repository;

import com.aurix.platform.customer.AurixCustomerApplication;
import com.aurix.platform.customer.onboarding.entity.Empresa;
import com.aurix.platform.customer.onboarding.entity.SituacaoCNPJ;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = AurixCustomerApplication.class)
@ActiveProfiles("test")
class EmpresaRepositoryTest {

    @Autowired
    private EmpresaRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.execute("DELETE FROM aurix.empresas");
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    @Test
    void saveEDevePersistirEmpresa() {
        Empresa empresa = Empresa.builder()
                .solicitacaoId(1L)
                .cnpj("12345678000190")
                .razaoSocial("Empresa Teste Ltda")
                .situacaoCadastral(SituacaoCNPJ.ATIVA)
                .build();

        Empresa saved = repository.save(empresa);

        assertNotNull(saved.getId());
        assertEquals(1L, saved.getSolicitacaoId());
        assertEquals("12345678000190", saved.getCnpj());
        assertNotNull(saved.getDataCriacao());
        assertNotNull(saved.getDataAtualizacao());
    }

    @Test
    void findBySolicitacaoIdDeveRetornarEmpresa() {
        Empresa empresa = Empresa.builder()
                .solicitacaoId(10L)
                .cnpj("12345678000190")
                .razaoSocial("Empresa Ltda")
                .build();
        repository.save(empresa);

        Optional<Empresa> found = repository.findBySolicitacaoId(10L);

        assertTrue(found.isPresent());
        assertEquals("12345678000190", found.get().getCnpj());
    }

    @Test
    void findBySolicitacaoIdDeveRetornarEmptyQuandoNaoExiste() {
        Optional<Empresa> found = repository.findBySolicitacaoId(999L);
        assertFalse(found.isPresent());
    }

    @Test
    void findByCnpjDeveRetornarEmpresa() {
        Empresa empresa = Empresa.builder()
                .solicitacaoId(2L)
                .cnpj("22345678000190")
                .razaoSocial("Empresa Ltda")
                .build();
        repository.save(empresa);

        Optional<Empresa> found = repository.findByCnpj("22345678000190");

        assertTrue(found.isPresent());
        assertEquals("Empresa Ltda", found.get().getRazaoSocial());
    }

    @Test
    void findByCnpjDeveRetornarEmptyQuandoNaoExiste() {
        Optional<Empresa> found = repository.findByCnpj("00000000000000");
        assertFalse(found.isPresent());
    }
}
