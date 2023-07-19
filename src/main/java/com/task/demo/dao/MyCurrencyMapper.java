package com.task.demo.dao;

import com.task.demo.models.MyCurrency;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyCurrencyMapper implements RowMapper<MyCurrency> {

    @Override
    public MyCurrency mapRow(ResultSet rs, int rowNum) throws SQLException {
        MyCurrency myCurrency = new MyCurrency();
        myCurrency.setId(rs.getInt("id"));
        myCurrency.setName(rs.getString("name"));
        myCurrency.setScale(rs.getInt("scale"));
        myCurrency.setSellRate(rs.getDouble("sell_rate"));
        myCurrency.setBuyRate(rs.getDouble("buy_rate"));

        return myCurrency;
    }
}
