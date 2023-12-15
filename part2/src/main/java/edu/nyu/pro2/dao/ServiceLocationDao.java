package edu.nyu.pro2.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ServiceLocationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> getAllAddress() {
        String sql = "SELECT address FROM servicelocation WHERE isdeleted = 0";
        return jdbcTemplate.query(sql, new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("address");
            }
        });
    }

    public List<String> findLocationsByUserId(String userId) {
        String sql = "SELECT address FROM servicelocation WHERE cid = ?"; // 假设有一个与用户ID相关联的字段
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> rs.getString("address"));
    }


}
