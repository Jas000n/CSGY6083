package edu.nyu.pro2.dao;

import edu.nyu.pro2.dto.DeviceDto;
import edu.nyu.pro2.entity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DeviceDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<DeviceDto> findAllDevicesWithDetails() {
        String sql = "SELECT d.did, dt.name, dt.model, sl.address, d.status, d.value1, d.value2,d.versionID " +
                "FROM device d " +
                "JOIN devicetype dt ON d.dtid = dt.dtid " +
                "JOIN servicelocation sl ON d.slid = sl.slid " +
                "WHERE d.isDeleted = 0";

        return jdbcTemplate.query(sql, new RowMapper<DeviceDto>() {
            public DeviceDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                DeviceDto device = new DeviceDto();
                device.setDid(rs.getString("did"));
                device.setName(rs.getString("name"));
                device.setModel(rs.getString("model"));
                device.setAddress(rs.getString("address"));
                device.setStatus(rs.getString("status"));
                device.setValue1(rs.getString("value1"));
                device.setValue2(rs.getString("value2"));
                device.setVersionID(rs.getInt("versionID"));
                return device;
            }
        });



    }
}
