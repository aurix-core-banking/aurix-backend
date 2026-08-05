package com.aurix.platform.customer.onboarding.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SolicitacaoPJTest {

    @Test
    void builderDeveCriarSolicitacaoPJComTodosOsCampos() {
        SolicitacaoPJ pj = SolicitacaoPJ.builder()
                .id(1L)
                .solicitacaoId(10L)
                .cnpj("12345678000190")
                .razaoSocial("Empresa Teste Ltda")
                .nomeFantasia("Teste")
                .naturezaJuridica("Sociedade Empresária Limitada")
                .porte(PorteEmpresa.EPP)
                .capitalSocial(BigDecimal.valueOf(100000))
                .dataConstituicao(LocalDate.of(2020, 1, 1))
                .inscricaoEstadual("123456789")
                .inscricaoMunicipal("987654")
                .faturamentoMensal(BigDecimal.valueOf(50000))
                .numeroFuncionarios(15)
                .clienteIdCriado(100L)
                .contaIdCriada(200L)
                .observacoesAnalista("Cliente verificado")
                .build();

        assertEquals(1L, pj.getId());
        assertEquals(10L, pj.getSolicitacaoId());
        assertEquals("12345678000190", pj.getCnpj());
        assertEquals("Empresa Teste Ltda", pj.getRazaoSocial());
        assertEquals("Teste", pj.getNomeFantasia());
        assertEquals("Sociedade Empresária Limitada", pj.getNaturezaJuridica());
        assertEquals(PorteEmpresa.EPP, pj.getPorte());
        assertEquals(BigDecimal.valueOf(100000), pj.getCapitalSocial());
        assertEquals(LocalDate.of(2020, 1, 1), pj.getDataConstituicao());
        assertEquals("123456789", pj.getInscricaoEstadual());
        assertEquals("987654", pj.getInscricaoMunicipal());
        assertEquals(BigDecimal.valueOf(50000), pj.getFaturamentoMensal());
        assertEquals(15, pj.getNumeroFuncionarios());
        assertEquals(100L, pj.getClienteIdCriado());
        assertEquals(200L, pj.getContaIdCriada());
        assertEquals("Cliente verificado", pj.getObservacoesAnalista());
    }

    @Test
    void builderSemCamposOpcionaisDeveCriarSolicitacaoPJ() {
        SolicitacaoPJ pj = SolicitacaoPJ.builder()
                .solicitacaoId(1L)
                .cnpj("12345678000190")
                .razaoSocial("Empresa Ltda")
                .build();

        assertEquals(1L, pj.getSolicitacaoId());
        assertEquals("12345678000190", pj.getCnpj());
        assertEquals("Empresa Ltda", pj.getRazaoSocial());
        assertNull(pj.getNomeFantasia());
        assertNull(pj.getPorte());
    }

    @Test
    void noArgConstructorDeveCriarInstanciaVazia() {
        SolicitacaoPJ pj = new SolicitacaoPJ();
        assertNotNull(pj);
        assertNull(pj.getId());
    }

    @Test
    void settersEDevemAtualizarCampos() {
        SolicitacaoPJ pj = new SolicitacaoPJ();
        pj.setId(1L);
        pj.setCnpj("12345678000190");
        pj.setRazaoSocial("Razao");
        pj.setPorte(PorteEmpresa.MEI);

        assertEquals(1L, pj.getId());
        assertEquals("12345678000190", pj.getCnpj());
        assertEquals("Razao", pj.getRazaoSocial());
        assertEquals(PorteEmpresa.MEI, pj.getPorte());
    }

    @Test
    void builderToStringDeveConterCampos() {
        SolicitacaoPJ.SolicitacaoPJBuilder builder = SolicitacaoPJ.builder()
                .cnpj("12345678000190")
                .razaoSocial("Teste");
        String str = builder.toString();
        assertTrue(str.contains("cnpj"));
        assertTrue(str.contains("12345678000190"));
        assertTrue(str.contains("razaoSocial"));
    }

    @Test
    void dataCriacaoEDataAtualizacaoDevemSerAnotadas() throws Exception {
        var dataCriacaoField = SolicitacaoPJ.class.getDeclaredField("dataCriacao");
        var dataAtualizacaoField = SolicitacaoPJ.class.getDeclaredField("dataAtualizacao");

        assertNotNull(dataCriacaoField.getAnnotation(org.hibernate.annotations.CreationTimestamp.class));
        assertNotNull(dataAtualizacaoField.getAnnotation(org.hibernate.annotations.UpdateTimestamp.class));
    }
}
