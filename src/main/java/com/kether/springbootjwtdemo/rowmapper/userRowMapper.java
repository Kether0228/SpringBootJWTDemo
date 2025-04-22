package com.kether.springbootjwtdemo.rowmapper;

import com.kether.springbootjwtdemo.model.user;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class userRowMapper implements RowMapper<user> {
    @Override
    public user mapRow(ResultSet rs, int rowNum) throws SQLException {
        user user = new user();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("user"));
        user.setPassword(rs.getString("password"));

        return user;
    }
}
