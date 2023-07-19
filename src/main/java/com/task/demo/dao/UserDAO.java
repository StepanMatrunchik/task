package com.task.demo.dao;

import com.task.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserDAO {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUserByUsername(String username){
        try {
            return jdbcTemplate.queryForObject("select * from public.users where username=?",
                    new UserMapper(),username);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}
