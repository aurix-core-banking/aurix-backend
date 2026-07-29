package com.aurix.platform.ai.agent;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Agentes LangChain4j para o Aurix Platform.
 *
 * <p>Implementa agentes com papéis especializados usando a API declarativa
 * do LangChain4j (interfaces + anotações). Cada agente tem:
 * <ul>
 *   <li>System prompt com papel e instruções</li>
 *   <li>Memória de conversa (sliding window)</li>
 *   <li>Ferramentas (tools) injetadas via {@link Tool}</li>
 * </ul>
 *
 * <h3>Agentes disponíveis:</h3>
 * <ul>
 *   <li>{@link CreditAnalysisAgent} — análise de risco de crédito</li>
 *   <li>{@link ComplianceAgent} — verificação de conformidade regulatória</li>
 *   <li>{@link FraudInvestigatorAgent} — investigação de operações suspeitas</li>
 * </ul>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AurixAgentFactory {

    private final ChatLanguageModel chatModel;

    // -------------------------------------------------------------------------
    // Interfaces de Agentes (LangChain4j AI Services)
    // -------------------------------------------------------------------------

    /**
     * Agente analista de crédito — avalia risco e sugere decisão.
     */
    interface CreditAnalysisAgent {
        @SystemMessage("""
            Você é um analista sênior de crédito do Banco Aurix com 15 anos de experiência.
            Avalie pedidos de crédito considerando: score de crédito, renda, histórico,
            comprometimento de renda e regulamentação BACEN.
            Responda sempre com: DECISÃO (APROVADO/REPROVADO/ANÁLISE_MANUAL),
            SCORE_RISCO (0-100), LIMITE_SUGERIDO e JUSTIFICATIVA detalhada.
            """)
        String analisarCredito(
            @UserMessage("Analise o seguinte pedido de crédito:\n{{pedido}}") 
            @V("pedido") String pedido
        );

        @SystemMessage("""
            Você é analista sênior de crédito do Banco Aurix.
            Com base no histórico fornecido, identifique padrões de risco.
            """)
        CreditRiskReport gerarRelatorioRisco(
            @UserMessage("Histórico do cliente: {{historico}}")
            @V("historico") String historico
        );
    }

    /**
     * Agente especialista em compliance — verifica conformidade regulatória.
     */
    interface ComplianceAgent {
        @SystemMessage("""
            Você é especialista em compliance bancário brasileiro do Banco Aurix.
            Conhece profundamente: BACEN, CMN, CVM, LGPD, COAF, normas de AML/KYC,
            Open Finance (BC#166/2021) e Resolução BCB nº 96/2021 (PIX).
            Para cada operação, verifique conformidade e cite a norma específica.
            """)
        ComplianceCheckResult verificarConformidade(
            @UserMessage("""
                Verifique a conformidade da seguinte operação:
                Tipo: {{tipo}}
                Valor: R$ {{valor}}
                Partes: {{partes}}
                Contexto: {{contexto}}
                """)
            @V("tipo") String tipo,
            @V("valor") String valor,
            @V("partes") String partes,
            @V("contexto") String contexto
        );
    }

    /**
     * Agente investigador de fraude — analisa operações suspeitas.
     */
    interface FraudInvestigatorAgent {
        @SystemMessage("""
            Você é investigador sênior de fraudes do Banco Aurix, especializado em
            detecção de lavagem de dinheiro (AML), fraudes em PIX, engenharia social
            e anéis de fraude. Analise padrões, identifique red flags e recomende ações.
            Classifique o risco: BAIXO / MÉDIO / ALTO / CRÍTICO.
            """)
        FraudAnalysisResult investigarOperacao(
            @UserMessage("""
                Investigue a seguinte operação suspeita:
                {{operacao}}
                
                Histórico recente do cliente (últimas 24h):
                {{historico}}
                """)
            @V("operacao") String operacao,
            @V("historico") String historico
        );
    }

    // -------------------------------------------------------------------------
    // Records de resposta estruturada
    // -------------------------------------------------------------------------

    public record CreditRiskReport(
        String decisao,
        int scoreRisco,
        double limiteSugerido,
        String justificativa,
        List<String> fatoresRisco,
        List<String> fatoresPositivos
    ) {}

    public record ComplianceCheckResult(
        boolean conforme,
        String status,       // APROVADO / PENDENTE / BLOQUEADO
        String normaAplicavel,
        String justificativa,
        List<String> acoesSugeridas
    ) {}

    public record FraudAnalysisResult(
        String nivelRisco,   // BAIXO / MÉDIO / ALTO / CRÍTICO
        List<String> redFlags,
        String recomendacao,
        boolean bloquearOperacao,
        String justificativa
    ) {}

    // -------------------------------------------------------------------------
    // Factory methods — criam agentes com memória isolada por sessão
    // -------------------------------------------------------------------------

    /**
     * Cria um agente de análise de crédito com memória de 10 mensagens.
     *
     * <pre>{@code
     * CreditAnalysisAgent agente = factory.creditAnalysisAgent();
     * String resultado = agente.analisarCredito(pedidoJson);
     * }</pre>
     */
    public CreditAnalysisAgent creditAnalysisAgent() {
        return AiServices.builder(CreditAnalysisAgent.class)
            .chatLanguageModel(chatModel)
            .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
            .build();
    }

    /**
     * Cria um agente de compliance com ferramentas de consulta regulatória.
     *
     * @param tools Ferramentas adicionais (ex: ConsultaBacenTool, ConsultaCoafTool).
     */
    public ComplianceAgent complianceAgent(Object... tools) {
        var builder = AiServices.builder(ComplianceAgent.class)
            .chatLanguageModel(chatModel)
            .chatMemory(MessageWindowChatMemory.withMaxMessages(20));
        if (tools.length > 0) {
            builder.tools(tools);
        }
        return builder.build();
    }

    /**
     * Cria um agente investigador de fraude.
     *
     * @param tools Ferramentas adicionais (ex: ConsultaTransacaoTool, BloqueioContaTool).
     */
    public FraudInvestigatorAgent fraudInvestigatorAgent(Object... tools) {
        var builder = AiServices.builder(FraudInvestigatorAgent.class)
            .chatLanguageModel(chatModel)
            .chatMemory(MessageWindowChatMemory.withMaxMessages(15));
        if (tools.length > 0) {
            builder.tools(tools);
        }
        return builder.build();
    }
}
