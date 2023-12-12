package edu.nyu.pro2.dao;

import edu.nyu.pro2.entity.customer;
import edu.nyu.pro2.entity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class deviceDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class DeviceMapper implements RowMapper<Device> {
        public Device mapRow(ResultSet rs, int rowNum) throws SQLException {
            Device device = new Device();
            device.setDid(rs.getString("did"));
            device.setDtid(rs.getString("dtid"));
            device.setSlid(rs.getString("slid"));
            device.setStatus(rs.getString("status"));
            device.setValue1(rs.getString("value1"));
            device.setValue2(rs.getString("value2"));
            device.setIsDeleted(rs.getInt("isDeleted"));
            return device;
        }
    }

    public List<Device> findAll() {
        return jdbcTemplate.query("SELECT * FROM device WHERE isDeleted = 0", new DeviceMapper());
    }

}
