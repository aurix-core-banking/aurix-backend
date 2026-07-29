package com.aurix.platform.banking.salario.service;

import com.aurix.platform.banking.BankingApplication;
import com.aurix.platform.banking.salario.client.CnabParser;
import com.aurix.platform.banking.salario.config.TestSalarioConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BankingApplication.class)
@ActiveProfiles("test")
@Import(TestSalarioConfig.class)
class CnabParserTest {

    @Autowired
    private CnabParser cnabParser;

    @Test
    void deveParsearArquivoCNABValido() throws Exception {
        var resource = new ClassPathResource("cnab/folha-valida.txt");
        var resultado = cnabParser.parse("folha-valida.txt", resource.getInputStream());

        assertThat(resultado).isNotNull();
        assertThat(resultado.arquivoNome()).isEqualTo("folha-valida.txt");
        assertThat(resultado.totalFuncionarios()).isPositive();
        assertThat(resultado.valorTotal()).isPositive();
        assertThat(resultado.detalhes()).isNotEmpty();
        assertThat(resultado.detalhes().get(0).cpf()).isNotBlank();
    }
}
