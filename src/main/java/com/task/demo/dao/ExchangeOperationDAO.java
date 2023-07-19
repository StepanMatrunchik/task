package com.task.demo.dao;

import com.task.demo.models.ExchangeOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;
import java.util.List;

@Component
public class ExchangeOperationDAO {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public ExchangeOperationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ExchangeOperation> list(){
        return jdbcTemplate.query("select * from public.exchange_operations", new ExchangeOperationMapper());
    }

    public void saveExchangeOperation(ExchangeOperation exchangeOperation){
        jdbcTemplate.update("insert into public.exchange_operations (source_currency,target_currency,price,source_amount,target_amount,date_time) values (?,?,?,?,?,?)",
                exchangeOperation.getSourceCurrency(),
                exchangeOperation.getTargetCurrency(),
                exchangeOperation.getPrice(),
                exchangeOperation.getSourceAmount(),
                exchangeOperation.getTargetAmount(),
                Timestamp.valueOf(exchangeOperation.getDateTime()));
    }
}
