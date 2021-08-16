package com.example.datasource.config.datasource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

public interface DataSourceConfig {
    DataSourceProperties dataSourceProperties();

    DataSource dataSource(DataSourceProperties properties);

    JdbcTemplate jdbcTemplate(DataSource ccbsDataSource);

    PlatformTransactionManager platformTransactionManager(DataSource dataSource);
}
