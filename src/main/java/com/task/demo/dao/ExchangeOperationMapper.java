package com.task.demo.dao;

import com.task.demo.models.ExchangeOperation;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ExchangeOperationMapper implements RowMapper<ExchangeOperation> {

    @Override
    public ExchangeOperation mapRow(ResultSet rs, int rowNum) throws SQLException {
        ExchangeOperation exchangeOperation = new ExchangeOperation();
        exchangeOperation.setId(rs.getInt("id"));
        exchangeOperation.setSourceCurrency(rs.getString("source_currency"));
        exchangeOperation.setTargetCurrency(rs.getString("target_currency"));
        exchangeOperation.setPrice(rs.getDouble("price"));
        exchangeOperation.setSourceAmount(rs.getDouble("source_amount"));
        exchangeOperation.setTargetAmount(rs.getDouble("target_amount"));
        exchangeOperation.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
        return exchangeOperation;
    }
}
