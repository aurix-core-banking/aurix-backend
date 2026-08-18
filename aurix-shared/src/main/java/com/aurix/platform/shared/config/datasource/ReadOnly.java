package com.aurix.platform.shared.config.datasource;

import java.lang.annotation.*;

/**
 * Marca um metodo ou classe como somente leitura.
 * O DataSource sera roteado para a replica de leitura.
 *
 * Uso: @ReadOnly no metodo ou classe de servico.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ReadOnly {
}
