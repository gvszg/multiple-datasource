package com.example.datasource.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EcOrderMapper implements RowMapper<EcOrder> {
    @Override
    public EcOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
        EcOrder order = EcOrder.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .totalPrice(rs.getBigDecimal("total_price"))
                .createTime(rs.getLong("create_time"))
                .updateTime(rs.getLong("update_time"))
                .build();
        return order;
    }
}
