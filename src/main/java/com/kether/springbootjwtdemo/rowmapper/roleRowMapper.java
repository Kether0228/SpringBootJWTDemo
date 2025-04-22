package com.kether.springbootjwtdemo.rowmapper;

import com.kether.springbootjwtdemo.model.role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class roleRowMapper implements RowMapper<role> {
    @Override
    public role mapRow(ResultSet rs, int rowNum) throws SQLException {
        role role = new role();
        role.setRole_id(rs.getInt("role_id"));
        role.setRole(rs.getString("role"));
        return role;
    }
}
