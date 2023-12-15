package edu.nyu.pro2.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DeviceTypeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> getAllName() {
        String sql = "SELECT name FROM devicetype WHERE isdeleted = 0";
        return jdbcTemplate.query(sql, new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("name");
            }
        });
    }

    public List<String> getAllModel() {
        String sql = "SELECT model FROM devicetype WHERE isdeleted = 0";
        return jdbcTemplate.query(sql, new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("model");
            }
        });
    }

    public List<String> getModelsByType(String type) {
        String sql = "SELECT model FROM devicetype WHERE name = ? AND isdeleted = 0";
        return jdbcTemplate.query(sql, new Object[]{type}, (rs, rowNum) -> rs.getString("model"));
    }

}
