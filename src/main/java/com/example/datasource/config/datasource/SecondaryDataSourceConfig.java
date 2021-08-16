package com.example.datasource.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class SecondaryDataSourceConfig implements DataSourceConfig {

    @Bean(name = "secondaryProperties")
    @ConfigurationProperties(prefix = "spring.secondary.datasource")
    @Override
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "secondaryDataSource")
    @Override
    public DataSource dataSource(@Qualifier("secondaryProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "secondaryJdbcTemplate")
    @Override
    public JdbcTemplate jdbcTemplate(@Qualifier("secondaryDataSource") DataSource ccbsDataSource) {
        return new JdbcTemplate(ccbsDataSource);
    }

    @Bean(name = "secondaryTxManager")
    @Override
    public PlatformTransactionManager platformTransactionManager(@Qualifier("secondaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
