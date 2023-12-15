package edu.nyu.pro2.dao;

import edu.nyu.pro2.entity.EnergyPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EnergyPriceDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper to map ResultSet to EnergyPrice entity
    private static final class EnergyPriceRowMapper implements RowMapper<EnergyPrice> {
        public EnergyPrice mapRow(ResultSet rs, int rowNum) throws SQLException {
            EnergyPrice energyPrice = new EnergyPrice();
            energyPrice.setEpid(rs.getString("epid"));
            energyPrice.setZipcode(rs.getString("zipcode"));
            energyPrice.setPrice(rs.getLong("price"));
            energyPrice.setTime(rs.getTimestamp("time"));
            energyPrice.setIsDeleted(rs.getInt("isDeleted"));
            return energyPrice;
        }
    }

    public List<EnergyPrice> findAll() {
        String sql = "SELECT * FROM EnergyPrice WHERE isDeleted = 0";
        return jdbcTemplate.query(sql, new EnergyPriceRowMapper());
    }

}
