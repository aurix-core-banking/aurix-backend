package com.aurix.platform.shared.config.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(-1)
public class ReadOnlyAspect {

    private static final Logger log = LoggerFactory.getLogger(ReadOnlyAspect.class);

    @Around("@annotation(com.aurix.platform.shared.config.datasource.ReadOnly)")
    public Object rotearLeitura(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            RoutingDataSource.setRead();
            log.debug("Roteando para replica de leitura: {}", joinPoint.getSignature().toShortString());
            return joinPoint.proceed();
        } finally {
            RoutingDataSource.clear();
        }
    }
}
