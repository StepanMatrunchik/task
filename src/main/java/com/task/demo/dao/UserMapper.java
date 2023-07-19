package com.task.demo.dao;

import com.task.demo.models.User;
import com.task.demo.models.enums.Role;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        String roles = rs.getString("roles");
        for(String role: roles.split(",")){
            user.addRole(Role.valueOf(role));
        }
        return user;
    }
}
