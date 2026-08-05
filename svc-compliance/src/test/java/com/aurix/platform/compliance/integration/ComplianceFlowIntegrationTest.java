package com.aurix.platform.compliance.integration;

import com.aurix.platform.compliance.ComplianceApplication;
import com.aurix.platform.compliance.entity.ConsentimentoLGPD;
import com.aurix.platform.shared.dto.RegulacaoDTO;
import com.aurix.platform.shared.entity.Regulacao;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ComplianceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ComplianceFlowIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private com.aurix.platform.compliance.repository.RegulacaoRepository regulacaoRepository;

    @Autowired
    private com.aurix.platform.compliance.repository.ConsentimentoLGPDRepository consentimentoRepository;

    private RestTemplate rest;

    @BeforeEach
    void setUp() {
        consentimentoRepository.deleteAll();
        regulacaoRepository.deleteAll();

        rest = new RestTemplate();
    }

    private String url(String path) {
        return "http://localhost:" + port + "/api/compliance" + path;
    }

    private RegulacaoDTO criarRegulacaoHelper(String nome, String orgao, Regulacao.TipoRegulacao tipo) {
        RegulacaoDTO dto = new RegulacaoDTO();
        dto.setNome(nome);
        dto.setDescricao("Descricao " + nome);
        dto.setOrgaoRegulador(orgao);
        dto.setNumeroRegulamentacao("NR-" + System.currentTimeMillis());
        dto.setDataVigencia(LocalDateTime.now().minusDays(30));
        dto.setDataVencimento(LocalDateTime.now().plusDays(335));
        dto.setTipoRegulacao(tipo);
        dto.setStatus(Regulacao.StatusRegulacao.ATIVA);
        ResponseEntity<RegulacaoDTO> response = rest.postForEntity(url("/regulacoes"), dto, RegulacaoDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        return response.getBody();
    }

    @Test
    void testCriarRegulacao() {
        RegulacaoDTO dto = new RegulacaoDTO();
        dto.setNome("Lei Geral de Protecao de Dados");
        dto.setDescricao("Lei que regula o tratamento de dados pessoais");
        dto.setOrgaoRegulador("ANPD");
        dto.setNumeroRegulamentacao("Lei 13.709/2018");
        dto.setDataVigencia(LocalDateTime.now().minusDays(365));
        dto.setTipoRegulacao(Regulacao.TipoRegulacao.LEI);
        dto.setStatus(Regulacao.StatusRegulacao.ATIVA);

        ResponseEntity<RegulacaoDTO> response = rest.postForEntity(url("/regulacoes"), dto, RegulacaoDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getNome()).isEqualTo("Lei Geral de Protecao de Dados");
        assertThat(response.getBody().getStatus()).isEqualTo(Regulacao.StatusRegulacao.ATIVA);
    }

    @Test
    void testBuscarRegulacaoPorId() {
        RegulacaoDTO created = criarRegulacaoHelper("Lei LGPD", "ANPD", Regulacao.TipoRegulacao.LEI);

        ResponseEntity<RegulacaoDTO> response = rest.getForEntity(
            url("/regulacoes/" + created.getId()), RegulacaoDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(created.getId());
        assertThat(response.getBody().getNome()).isEqualTo("Lei LGPD");
    }

    @Test
    void testListarRegulacoes() {
        criarRegulacaoHelper("Regulacao BACEN 1", "BACEN", Regulacao.TipoRegulacao.CIRCULAR);
        criarRegulacaoHelper("Regulacao BACEN 2", "BACEN", Regulacao.TipoRegulacao.RESOLUCAO);

        ResponseEntity<RegulacaoDTO[]> response = rest.getForEntity(url("/regulacoes"), RegulacaoDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Arrays.asList(response.getBody())).hasSize(2);
    }

    @Test
    void testListarRegulacoesPorOrgao() {
        criarRegulacaoHelper("Res CMN 1", "CMN", Regulacao.TipoRegulacao.RESOLUCAO);
        criarRegulacaoHelper("Circ BACEN 1", "BACEN", Regulacao.TipoRegulacao.CIRCULAR);

        ResponseEntity<RegulacaoDTO[]> response = rest.getForEntity(
            url("/regulacoes/orgao/BACEN"), RegulacaoDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Arrays.asList(response.getBody())).hasSize(1);
    }

    @Test
    void testListarRegulacoesPorTipo() {
        criarRegulacaoHelper("Lei 1", "ANPD", Regulacao.TipoRegulacao.LEI);
        criarRegulacaoHelper("Circular 1", "BACEN", Regulacao.TipoRegulacao.CIRCULAR);

        ResponseEntity<RegulacaoDTO[]> response = rest.getForEntity(
            url("/regulacoes/tipo/" + Regulacao.TipoRegulacao.LEI), RegulacaoDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Arrays.asList(response.getBody())).hasSize(1);
    }

    @Test
    void testListarRegulacoesAtivas() {
        criarRegulacaoHelper("Regulacao Ativa", "BACEN", Regulacao.TipoRegulacao.LEI);

        ResponseEntity<RegulacaoDTO[]> response = rest.getForEntity(
            url("/regulacoes/ativas"), RegulacaoDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Arrays.asList(response.getBody())).isNotEmpty();
    }

    @Test
    void testToggleStatusRegulacao() {
        RegulacaoDTO created = criarRegulacaoHelper("Regulacao Toggle", "BACEN", Regulacao.TipoRegulacao.LEI);

        rest.put(url("/regulacoes/" + created.getId() + "/toggle-status"), null);

        ResponseEntity<RegulacaoDTO> response = rest.getForEntity(
            url("/regulacoes/" + created.getId()), RegulacaoDTO.class);
        assertThat(response.getBody().getStatus()).isEqualTo(Regulacao.StatusRegulacao.SUSPENSA);
    }

    @Test
    void testCriarConsentimentoLGPD() {
        ResponseEntity<ConsentimentoLGPD> response = rest.postForEntity(
            url("/api/lgpd/consentimentos?clienteId=1&cpfCnpj=52998224725&tipo=COLETA_DADOS&descricaoFinalidade=Coleta+de+dados+para+analise"),
            null, ConsentimentoLGPD.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCodigoConsentimento()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(ConsentimentoLGPD.StatusConsentimento.PENDENTE);
    }

    @Test
    void testConcederConsentimentoLGPD() {
        ResponseEntity<ConsentimentoLGPD> created = rest.postForEntity(
            url("/api/lgpd/consentimentos?clienteId=1&cpfCnpj=52998224725&tipo=COLETA_DADOS&descricaoFinalidade=Coleta+de+dados"),
            null, ConsentimentoLGPD.class);
        String codigo = created.getBody().getCodigoConsentimento();

        ResponseEntity<ConsentimentoLGPD> response = rest.postForEntity(
            url("/api/lgpd/consentimentos/" + codigo + "/conceder"),
            null, ConsentimentoLGPD.class);
        assertThat(response.getBody().getStatus()).isEqualTo(ConsentimentoLGPD.StatusConsentimento.CONCEDIDO);
        assertThat(response.getBody().getDataConsentimento()).isNotNull();
    }

    @Test
    void testListarConsentimentosPorCliente() {
        rest.postForEntity(
            url("/api/lgpd/consentimentos?clienteId=1&cpfCnpj=52998224725&tipo=COLETA_DADOS&descricaoFinalidade=Finalidade+1"),
            null, ConsentimentoLGPD.class);
        rest.postForEntity(
            url("/api/lgpd/consentimentos?clienteId=1&cpfCnpj=52998224725&tipo=PROCESSAMENTO_DADOS&descricaoFinalidade=Finalidade+2"),
            null, ConsentimentoLGPD.class);

        ResponseEntity<ConsentimentoLGPD[]> response = rest.getForEntity(
            url("/api/lgpd/consentimentos/cliente/1"), ConsentimentoLGPD[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Arrays.asList(response.getBody())).hasSize(2);
    }
}
