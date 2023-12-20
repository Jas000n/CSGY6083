package edu.nyu.pro2.dao;

import edu.nyu.pro2.entity.ServiceLocation;
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

    public List<ServiceLocation> getAllServiceLocations() {
        String sql = "SELECT * FROM servicelocation WHERE isdeleted = 0";
        return jdbcTemplate.query(sql, new RowMapper<ServiceLocation>() {
            public ServiceLocation mapRow(ResultSet rs, int rowNum) throws SQLException {
                ServiceLocation location = new ServiceLocation();
                // 设置所有需要的字段
                location.setSlid(rs.getString("slid"));
                location.setState(rs.getString("state"));
                location.setCity(rs.getString("city"));
                location.setZipcode(rs.getString("zipcode"));
                location.setAddress(rs.getString("address"));
                location.setSquareFootage(rs.getString("squareFootage"));
                location.setNumberOfBedrooms(rs.getString("numberOfBedrooms"));
                location.setNumberOfOccupants(rs.getString("numberOfOccupants"));
                location.setStartDate(rs.getDate("startDate"));
                location.setIsDeleted(rs.getInt(("isDeleted")));
                location.setCid((rs.getString("cid")));
                return location;
            }
        });
    }

    public List<ServiceLocation> getAllServiceLocationsByUsers(String userId) {
        String sql = "SELECT * FROM servicelocation WHERE isdeleted = 0 AND cid = ? ";
        return jdbcTemplate.query(sql, new Object[]{userId},new RowMapper<ServiceLocation>() {
            public ServiceLocation mapRow(ResultSet rs, int rowNum) throws SQLException {
                ServiceLocation location = new ServiceLocation();
                // 设置所有需要的字段
                location.setSlid(rs.getString("slid"));
                location.setState(rs.getString("state"));
                location.setCity(rs.getString("city"));
                location.setZipcode(rs.getString("zipcode"));
                location.setAddress(rs.getString("address"));
                location.setSquareFootage(rs.getString("squareFootage"));
                location.setNumberOfBedrooms(rs.getString("numberOfBedrooms"));
                location.setNumberOfOccupants(rs.getString("numberOfOccupants"));
                location.setStartDate(rs.getDate("startDate"));
                location.setIsDeleted(rs.getInt(("isDeleted")));
                location.setCid((rs.getString("cid")));
                return location;
            }
        });
    }


    public List<String> findLocationsByUserId(String userId) {
        String sql = "SELECT address FROM servicelocation WHERE cid = ?"; // 假设有一个与用户ID相关联的字段
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> rs.getString("address"));
    }

    // 添加新的服务位置
    public int addServiceLocation(String address, String city, String state, String zipcode, int squareFootage, int numberOfBedrooms, int numberOfOccupants, int cid) {
        String sql = "INSERT INTO servicelocation (address, city, state, zipcode, squareFootage, numberOfBedrooms, numberOfOccupants, cid, isdeleted) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0)";
        return jdbcTemplate.update(sql, address, city, state, zipcode, squareFootage, numberOfBedrooms, numberOfOccupants, cid);
    }

    // 更新服务位置
    public int updateServiceLocation(int slid, String address, String city, String state, String zipcode, int squareFootage, int numberOfBedrooms, int numberOfOccupants, int cid) {
        String sql = "UPDATE servicelocation SET address = ?, city = ?, state = ?, zipcode = ?, squareFootage = ?, numberOfBedrooms = ?, numberOfOccupants = ?, cid = ? " +
                "WHERE slid = ? AND isdeleted = 0";
        return jdbcTemplate.update(sql, address, city, state, zipcode, squareFootage, numberOfBedrooms, numberOfOccupants, cid, slid);
    }

    // 删除服务位置
    public int deleteServiceLocation(int slid) {
        String sql = "UPDATE servicelocation SET isdeleted = 1 WHERE slid = ?";
        return jdbcTemplate.update(sql, slid);
    }




}
