package edu.nyu.pro2.dao;

import edu.nyu.pro2.dto.DeviceDto;
import edu.nyu.pro2.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class CustomerMapper implements RowMapper<Customer> {
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer customer = new Customer();
            customer.setCid(rs.getString("cid"));
            customer.setFirstName(rs.getString("firstname"));
            customer.setLastName((rs.getString("lastname")));
            customer.setBillingAddress(rs.getString("billingaddress"));
            customer.setIsDeleted(rs.getInt("isdeleted"));
            return customer;
        }
    }

    public Customer getCustomerById(String id) {
        String sql = "SELECT * FROM customer WHERE cid = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new CustomerMapper());
    }


    public List<String> getAllCids() {
        String sql = "SELECT cid FROM customer WHERE isdeleted = 0";
        return jdbcTemplate.query(sql, new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("cid");
            }
        });
    }




    }

    // Other CRUD methods

