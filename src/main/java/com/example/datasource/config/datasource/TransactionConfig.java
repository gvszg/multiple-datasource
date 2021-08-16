package com.example.datasource.config.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionSynchronization;

@Configuration
public class TransactionConfig {

    @Primary
    @Bean(name = "chainedTransactionManager")
    public ChainedTransactionManager transactionManager(
            @Qualifier("primaryTxManager") PlatformTransactionManager primaryTxManager,
            @Qualifier("secondaryTxManager") PlatformTransactionManager secondaryTxManager
    ) {
        return new ChainedTransactionManager(primaryTxManager, secondaryTxManager);
    }
}
