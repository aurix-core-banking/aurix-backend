package com.aurix.platform.shared.dto;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public record RespostaPaginada<T>(
        List<T> conteudo,
        int pagina,
        int tamanho,
        long totalElementos,
        int totalPaginas,
        boolean primeira,
        boolean ultima) {

    public static <T> RespostaPaginada<T> de(Page<T> page) {
        return new RespostaPaginada<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast());
    }

    public static <S, T> RespostaPaginada<T> de(Page<S> page, Function<S, T> mapper) {
        return new RespostaPaginada<>(
                page.getContent().stream().map(mapper).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast());
    }
}
