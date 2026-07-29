package com.aurix.platform.ai.llm;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.extern.slf4j.Slf4j;
// import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.time.Duration;

/**
 * Configuração da camada LLM do Aurix AI.
 *
 * <p>Registra beans de LLM para diferentes providers e ambientes:
 * <ul>
 *   <li><b>dev</b>: Ollama local (sem custo, sem credenciais)</li>
 *   <li><b>staging</b>: vLLM self-hosted</li>
 *   <li><b>prod</b>: AWS Bedrock (sa-east-1)</li>
 * </ul>
 *
 * <p>Expõe dois tipos de beans:
 * <ul>
 *   <li>{@link ChatClient} (Spring AI) — para uso simples com fluent API</li>
 *   <li>{@link ChatLanguageModel} (LangChain4j) — para agentes e chains</li>
 * </ul>
 */
@Slf4j
@Configuration
public class LlmConfig {

    // -------------------------------------------------------------------------
    // Spring AI ChatClient — fluent API para uso simples
    // Requires spring-ai-starter on classpath
    // -------------------------------------------------------------------------

    // @Bean
    // @Primary
    // @ConditionalOnClass(name = "org.springframework.ai.chat.client.ChatClient")
    // public ChatClient aurixDefaultChatClient(ChatClient.Builder builder) {
    //     return builder
    //         .defaultSystem("""
    //             Você é um assistente especializado do Banco Aurix, com profundo
    //             conhecimento em regulamentação financeira brasileira (BACEN, CMN, CVM),
    //             compliance bancário, análise de crédito, detecção de fraude e Open Finance.
    //             Responda sempre em português brasileiro de forma precisa e objetiva.
    //             Nunca revele informações de outros clientes ou dados internos do banco.
    //             """)
    //         .build();
    // }

    // -------------------------------------------------------------------------
    // LangChain4j ChatLanguageModel — para agentes e chains
    // -------------------------------------------------------------------------

    /**
     * Modelo LangChain4j via Ollama — ativo em perfil "dev".
     *
     * <p>Configurar modelo via {@code aurix.ai.ollama.model} no application.yml.
     * Padrão: {@code llama3.2} (rápido, ~2GB RAM).
     */
    @Bean("langchain4jOllamaModel")
    @Profile("dev")
    public ChatLanguageModel ollamaChatModel(
            @Value("${aurix.ai.ollama.base-url:http://localhost:11434}") String baseUrl,
            @Value("${aurix.ai.ollama.model:llama3.2}") String model,
            @Value("${aurix.ai.ollama.timeout-seconds:120}") int timeoutSeconds) {

        log.info("[AurixAI] Provider: Ollama | model={} | baseUrl={}", model, baseUrl);

        return OllamaChatModel.builder()
            .baseUrl(baseUrl)
            .modelName(model)
            .temperature(0.7)
            .timeout(Duration.ofSeconds(timeoutSeconds))
            .build();
    }

    /**
     * Modelo LangChain4j via OpenAI-compatível — ativo em perfil "staging".
     *
     * <p>Suporta qualquer API OpenAI-compatível (vLLM, LM Studio, Together AI).
     * Configurar {@code aurix.ai.openai.base-url} para apontar ao servidor.
     */
    @Bean("langchain4jOpenAiModel")
    @Profile("staging")
    public ChatLanguageModel openAiCompatModel(
            @Value("${aurix.ai.openai.base-url:https://api.openai.com/v1}") String baseUrl,
            @Value("${aurix.ai.openai.api-key:}") String apiKey,
            @Value("${aurix.ai.openai.model:gpt-4o-mini}") String model) {

        log.info("[AurixAI] Provider: OpenAI-compat | model={} | baseUrl={}", model, baseUrl);

        return OpenAiChatModel.builder()
            .baseUrl(baseUrl)
            .apiKey(apiKey.isBlank() ? "local" : apiKey)
            .modelName(model)
            .temperature(0.7)
            .build();
    }

    /**
     * Modelo LangChain4j via GPULlama3.java + TornadoVM — ativo em perfil "gpu".
     *
     * <p>Executa inferência de modelos GGUF diretamente na GPU via TornadoVM,
     * sem servidor externo (Ollama, vLLM). Puro Java + GPU kernels gerados em runtime.
     *
     * <p>Modelos suportados: Llama3, Mistral, Qwen2.5, Phi-3, IBM Granite (formato GGUF).
     *
     * <p>Requisitos:
     * <ul>
     *   <li>TornadoVM SDK instalado ({@code tornado-installer.py})</li>
     *   <li>JDK 21+ com {@code --enable-preview --add-modules=jdk.incubator.vector}</li>
     *   <li>NVIDIA GPU (CUDA) ou AMD GPU (OpenCL) ou Apple Silicon (Metal)</li>
     *   <li>Modelo GGUF baixado localmente</li>
     * </ul>
     *
     * @see <a href="https://github.com/beehive-lab/GPULlama3.java">GPULlama3.java</a>
     * @see <a href="https://tornadovm.readthedocs.io">TornadoVM Docs</a>
     */
    @Bean("langchain4jGpuModel")
    @Profile("gpu")
    public ChatLanguageModel gpuLlama3Model(
            @Value("${aurix.ai.gpullama.model-path}") String modelPath,
            @Value("${aurix.ai.gpullama.context-length:4096}") int contextLength) {

        log.info("[AurixAI] Provider: GPULlama3+TornadoVM | modelPath={}", modelPath);

        // GPULlama3.java integra diretamente com LangChain4j via sua interface
        // uk.ac.manchester.beehive.gpullama.langchain4j.GPULlama3ChatModel
        try {
            Class<?> gpuModelClass = Class.forName(
                "uk.ac.manchester.beehive.gpullama.langchain4j.GPULlama3ChatModel"
            );
            var builder = gpuModelClass.getMethod("builder").invoke(null);
            builder.getClass().getMethod("modelPath", String.class).invoke(builder, modelPath);
            builder.getClass().getMethod("contextLength", int.class).invoke(builder, contextLength);
            return (ChatLanguageModel) builder.getClass().getMethod("build").invoke(builder);
        } catch (Exception e) {
            log.warn("[AurixAI] GPULlama3.java não disponível ({}). "
                + "Verifique TornadoVM SDK e gpullama3-langchain4j no classpath.", e.getMessage());
            // Fallback para Ollama se GPU não disponível
            return OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("llama3.2")
                .build();
        }
    }
}
