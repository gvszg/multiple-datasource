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
public class PrimaryDataSourceConfig implements DataSourceConfig {

    // STEP 1
    @Bean(name = "primaryProperties")
    @ConfigurationProperties("spring.primary.datasource")
    @Override
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    // STEP 2
    @Bean(name = "primaryDataSource")
    @Override
    public DataSource dataSource(@Qualifier("primaryProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    // STEP 3
    @Bean(name = "primaryJdbcTemplate")
    @Override
    public JdbcTemplate jdbcTemplate(@Qualifier("primaryDataSource") DataSource ccbsDataSource) {
        return new JdbcTemplate(ccbsDataSource);
    }

    // STEP 4
    @Bean(name = "primaryTxManager")
    @Override
    public PlatformTransactionManager platformTransactionManager(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
