package com.aurix.platform.shared.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String writeUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${aurix.datasource.read.url:}")
    private String readUrl;

    @Value("${aurix.datasource.hikari.maximum-pool-size:30}")
    private int maxPoolSize;

    @Value("${aurix.datasource.hikari.minimum-idle:5}")
    private int minIdle;

    @Value("${aurix.datasource.hikari.read.maximum-pool-size:20}")
    private int readMaxPoolSize;

    @Primary
    @Bean
    public DataSource dataSource() {
        if (readUrl == null || readUrl.isBlank()) {
            return writeDataSource();
        }

        HikariDataSource writeDs = writeDataSource();
        HikariDataSource readDs = readDataSource();

        RoutingDataSource routing = new RoutingDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(RoutingDataSource.DataSourceType.WRITE, writeDs);
        targetDataSources.put(RoutingDataSource.DataSourceType.READ, readDs);

        routing.setTargetDataSources(targetDataSources);
        routing.setDefaultTargetDataSource(writeDs);
        return routing;
    }

    private HikariDataSource writeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(writeUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(maxPoolSize);
        config.setMinimumIdle(minIdle);
        config.setPoolName("aurix-write-pool");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);
    }

    private HikariDataSource readDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(readUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(readMaxPoolSize);
        config.setMinimumIdle(2);
        config.setPoolName("aurix-read-pool");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);
    }
}
