package edu.nyu.pro2.dao;

import edu.nyu.pro2.dto.DeviceDto;
import edu.nyu.pro2.entity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        String sql = "SELECT d.did, dt.name,dt.dtid, dt.model,sl.slid, sl.address, d.status, d.value1, d.value2,d.versionID " +
                "FROM device d " +
                "JOIN devicetype dt ON d.dtid = dt.dtid " +
                "JOIN servicelocation sl ON d.slid = sl.slid " +
                "WHERE d.isDeleted = 0";

        return jdbcTemplate.query(sql, new RowMapper<DeviceDto>() {
            public DeviceDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                DeviceDto device = new DeviceDto();
                device.setDid(rs.getString("did"));
                device.setDtid(rs.getString("dtid"));
                device.setSlid(rs.getString("slid"));
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

    // 根据设备类型名称和型号查找dtid
    public Integer findDeviceTypeIdByNameAndModel(String name, String model) {
        String sql = "SELECT dtid FROM devicetype WHERE name = ? AND model = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{name, model}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            // 这里可以根据业务需求处理异常，例如返回null或者抛出自定义异常
            return null;
        }
    }

    // 根据服务位置地址查找slid
    public Integer findServiceLocationIdByAddress(String address) {
        String sql = "SELECT slid FROM servicelocation WHERE address = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{address}, Integer.class);
    }

    private Integer getCurrentVersionId(String did) {
        try {
            String sql = "SELECT versionID FROM device WHERE did = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{did}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null; // 或者处理异常
        }
    }

    // 添加设备
    public int addDevice(DeviceDto deviceDto) {
        System.out.println("进入addDevice方法");
        System.out.println(deviceDto.getName());
        Integer dtid = findDeviceTypeIdByNameAndModel(deviceDto.getName(), deviceDto.getModel());
        Integer slid = findServiceLocationIdByAddress(deviceDto.getAddress());
        System.out.println(dtid);
        System.out.println(slid);

        if (dtid == null || slid == null) {
            // 处理dtid或slid无法找到的情况，例如抛出异常或返回错误码
        }

        // 请确认这里是否需要插入versionID和isDeleted
        String sql = "INSERT INTO device (dtid, slid, status,isDeleted,versionID) " +
                "VALUES ( ?, ?, ?, 0,?)";
        return jdbcTemplate.update(
                sql,
                dtid,
                slid,
                0,
                1
        );
    }

    // 更新设备
    public int updateDevice(String did, DeviceDto deviceDto) {
        Integer dtid = findDeviceTypeIdByNameAndModel(deviceDto.getName(), deviceDto.getModel());
        Integer slid = findServiceLocationIdByAddress(deviceDto.getAddress());
        // 获取当前的versionID
        Integer currentVersionId = getCurrentVersionId(did);
        if (currentVersionId == null) {
            // 处理找不到设备或versionID的情况
        }
        Integer newVersionId = currentVersionId + 1;

//        System.out.println(dtid);
//        System.out.println(slid);
//        System.out.println(did);
        String sql = "UPDATE device SET dtid = ?, slid = ? , versionID = ? " +
                "WHERE did = ? AND versionID = ?";
        return jdbcTemplate.update(
                sql,
                dtid,
                slid,
                newVersionId,
                did,
                currentVersionId
        );
    }

    public DeviceDto findDeviceDetailsById(String did) {
        String sql = "SELECT d.did, dt.name, dt.model, sl.address, d.status, d.value1, d.value2, d.versionID " +
                "FROM device d " +
                "JOIN devicetype dt ON d.dtid = dt.dtid " +
                "JOIN servicelocation sl ON d.slid = sl.slid " +
                "WHERE d.did = ? AND d.isDeleted = 0";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{did}, new RowMapper<DeviceDto>() {
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
        } catch (EmptyResultDataAccessException e) {
            return null; // 或者抛出自定义异常
        }
    }

    public int deleteDevice(String did) {
        String sql = "UPDATE device SET isDeleted = 1 WHERE did = ?";
        return jdbcTemplate.update(sql, did);
    }

    public List<DeviceDto> findDevicesByUserId(String userId) {
        String sql = "SELECT d.did, d.dtid, dt.name, dt.model, sl.slid, sl.address, d.status, d.value1, d.value2, d.versionID " +
                "FROM device d " +
                "JOIN devicetype dt ON d.dtid = dt.dtid " +
                "JOIN servicelocation sl ON d.slid = sl.slid " +
                "WHERE sl.cid = ? AND d.isDeleted = 0"; // 假设设备表中有uid字段
        return jdbcTemplate.query(sql, new Object[]{userId}, new RowMapper<DeviceDto>() {
            public DeviceDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                DeviceDto device = new DeviceDto();
                device.setDid(rs.getString("did"));
                device.setDtid(rs.getString("dtid"));
                device.setSlid(rs.getString("slid"));
                device.setName(rs.getString("name"));
                device.setModel(rs.getString("model"));
                device.setAddress(rs.getString("address"));
                device.setStatus(rs.getString("status"));
                device.setValue1(rs.getString("value1"));
                device.setValue2(rs.getString("value2"));
                device.setVersionID(rs.getInt("versionID"));;
                return device;
            }
        });
    }




}



