package com.task.demo.dao;

import com.task.demo.models.MyCurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyCurrencyDAO {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public MyCurrencyDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public synchronized List<MyCurrency> list(){
        return jdbcTemplate.query("select * from public.currencies", new MyCurrencyMapper());
    }

    public synchronized MyCurrency getMyCurrencyByCurrencyName(String curr_name){
        try {
            return jdbcTemplate.queryForObject("select * from public.currencies where name=?",
                    new MyCurrencyMapper(),curr_name);
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    public synchronized void addMyCurrency(MyCurrency myCurrency){
        jdbcTemplate.update("insert into public.currencies (name,scale,sell_rate,buy_rate) values (?,?,?,?)",
                myCurrency.getName(),
                myCurrency.getScale(),
                myCurrency.getSellRate(),
                myCurrency.getBuyRate());


    }

    public synchronized void updateCurrencies(List<MyCurrency> myCurrencies){
        for(MyCurrency myCurrency: myCurrencies){
            if(this.getMyCurrencyByCurrencyName(myCurrency.getName())==null){
                this.addMyCurrency(myCurrency);
            }
            else {
                jdbcTemplate.update("update public.currencies set sell_rate=?, buy_rate=? where name =?",
                        myCurrency.getSellRate(),
                        myCurrency.getBuyRate(),
                        myCurrency.getName()
                );
            }

        }
    }



}
