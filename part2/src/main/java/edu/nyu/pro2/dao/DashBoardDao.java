package edu.nyu.pro2.dao;

import edu.nyu.pro2.dto.EnergyConsumptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DashBoardDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class EnergyConsumptionMapper implements RowMapper<EnergyConsumptionDTO> {
        public EnergyConsumptionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            EnergyConsumptionDTO energyConsumptionDTO = new EnergyConsumptionDTO();
            //energyConsumptionDTO.setCid(rs.getString("cid")); // Ensure this field exists in your DTO
            energyConsumptionDTO.setDid(rs.getString("did")); // Assuming you have a 'did' field in your DTO
            energyConsumptionDTO.setTotalConsumption(rs.getString("TotalConsumption"));
            energyConsumptionDTO.setSlid(rs.getString("slid"));
            energyConsumptionDTO.setAddress(rs.getString("address"));
            energyConsumptionDTO.setSquarefootage(rs.getString("squarefootage"));
            return energyConsumptionDTO;
        }
    }

    public List<EnergyConsumptionDTO> getAllEnergyConsumption(String cid) {
        String sql = "WITH enrolled_devices AS (" +
                "    SELECT d.did, sl.slid, sl.address, sl.squarefootage, sl.zipcode\n" +
                "    FROM Device AS d\n" +
                "    INNER JOIN ServiceLocation AS sl ON d.slid = sl.slid\n" +
                "    INNER JOIN Customer AS c ON sl.cid = c.cid\n" +
                "    WHERE c.cid = ?\n" +
                ")\n" +
                "SELECT e.did, \n" +
                "       e.value1 * ep.price AS TotalConsumption,\n" +
                "       ed.slid,\n" +
                "       ed.address,\n" +
                "       ed.squarefootage\n" +
                "FROM Event AS e\n" +
                "INNER JOIN enrolled_devices AS ed ON e.did = ed.did\n" +
                "INNER JOIN EnergyPrice AS ep ON hour(e.timestamp) = hour(ep.time)\n" +
                "and day(e.timestamp) = day(ep.time) and month(e.timestamp) = month(ep.time) and year(e.timestamp) = year(ep.time)\n" +
                "WHERE \n" +
                "--   e.timestamp >= NOW() - INTERVAL 24 HOUR AND \n" +
                "   e.timestamp < NOW() \n" +
                "   AND e.label = 'Energy_Use'\n" +
                "   AND ed.zipcode = ep.zipcode";

        return jdbcTemplate.query(sql, new Object[]{cid}, new EnergyConsumptionMapper());
    }
}
