package com.example.datasource.service;

import com.example.datasource.model.EcOrder;
import com.example.datasource.model.EcOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
@Service
public class EcOrderService {
    private static final String createSql = "INSERT INTO ec_orders(user_id, total_price, create_time, update_time) VALUES(?,?,?,?);";
    private final JdbcTemplate primaryJdbcTemplate;
    private final JdbcTemplate secondaryJdbcTemplate;

    public EcOrderService(
            @Qualifier("primaryJdbcTemplate") JdbcTemplate primaryJdbcTemplate,
            @Qualifier("secondaryJdbcTemplate") JdbcTemplate secondaryJdbcTemplate)
    {
        this.primaryJdbcTemplate = primaryJdbcTemplate;
        this.secondaryJdbcTemplate = secondaryJdbcTemplate;
    }

    @Transactional(readOnly = true)
    public List<EcOrder> findAll() {
        return secondaryJdbcTemplate.query("select * from ec_orders;", new EcOrderMapper());
    }

    public EcOrder create(final EcOrder ecOrder) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        primaryJdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                Connection connection;
                PreparedStatement statement = con.prepareStatement(createSql, Statement.RETURN_GENERATED_KEYS);
                statement.setLong(1, ecOrder.getUserId());
                statement.setBigDecimal(2, ecOrder.getTotalPrice());
                statement.setLong(3, ecOrder.getCreateTime());
                statement.setLong(4, ecOrder.getUpdateTime());
                return statement;
            }
        }, keyHolder);

        long newOrderId = keyHolder.getKey().longValue();
        ecOrder.setId(newOrderId);

        return ecOrder;
    }
}
