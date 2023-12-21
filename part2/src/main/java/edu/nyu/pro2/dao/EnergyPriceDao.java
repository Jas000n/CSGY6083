package edu.nyu.pro2.dao;

import edu.nyu.pro2.dto.EnergyPriceDTO;
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
    private static final class EnergyPriceDTORowMapper implements RowMapper<EnergyPriceDTO> {
        public EnergyPriceDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            EnergyPriceDTO energyPriceDTO = new EnergyPriceDTO();
            energyPriceDTO.setTime(rs.getString("time"));
            energyPriceDTO.setPrice(rs.getString("price"));
            energyPriceDTO.setTimeSlot(rs.getString("TimeSlot"));

            return energyPriceDTO;
        }
    }

    public List<EnergyPrice> findAll() {
        String sql = "SELECT * FROM EnergyPrice WHERE isDeleted = 0";
        return jdbcTemplate.query(sql, new EnergyPriceRowMapper());
    }

    public List<EnergyPriceDTO> findLast7Day(){
        String sql = "SELECT DATE(EP.time) AS time, AVG(EP.price) AS price, 'NightTime' AS timeslot\n" +
                "FROM EnergyPrice EP\n" +
                "WHERE HOUR(EP.time) = 3 \n" +
                "    AND EP.time + INTERVAL 7 DAY > NOW() \n" +
                "    AND EP.time < NOW() \n" +
                "    AND EP.isdeleted = 0\n" +
                "GROUP BY DATE(EP.time)\n" +
                "\n" +
                "UNION ALL\n" +
                "\n" +
                "SELECT DATE(EP.time) AS time, AVG(EP.price) AS price, 'DayTime' AS timeslot\n" +
                "FROM EnergyPrice EP\n" +
                "WHERE HOUR(EP.time) = 12 \n" +
                "    AND EP.time + INTERVAL 7 DAY > NOW() \n" +
                "    AND EP.time < NOW() \n" +
                "    AND EP.isdeleted = 0\n" +
                "GROUP BY DATE(EP.time);\n";
        return jdbcTemplate.query(sql, new EnergyPriceDTORowMapper());
    }

    public List<EnergyPriceDTO> findLast7DayByID(String cid){
        String sql = """
                with qualified_zip as (    
                    select zipcode
                    from ServiceLocation
                    where cid = ?
                )
                SELECT DATE(EP.time) AS time, AVG(EP.price) AS price, 'NightTime' AS timeslot
                FROM EnergyPrice EP
                WHERE HOUR(EP.time) = 3 AND EP.time + INTERVAL 7 DAY > NOW() AND EP.time < NOW() AND EP.isdeleted = 0 and ep.zipcode in (select zipcode from qualified_zip)
                GROUP BY DATE(EP.time)
                UNION ALL
                SELECT DATE(EP.time) AS time, AVG(EP.price) AS price, 'DayTime' AS timeslot
                FROM EnergyPrice EP
                WHERE HOUR(EP.time) = 12 AND EP.time + INTERVAL 7 DAY > NOW() AND EP.time < NOW()  AND EP.isdeleted = 0 and ep.zipcode in (select zipcode from qualified_zip)
                GROUP BY DATE(EP.time);""";

        return jdbcTemplate.query(sql,new Object[]{cid}, new EnergyPriceDTORowMapper());
    }
}
