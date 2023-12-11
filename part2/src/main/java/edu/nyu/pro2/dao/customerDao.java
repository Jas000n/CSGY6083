package edu.nyu.pro2.dao;

import edu.nyu.pro2.entity.customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class customerDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class CustomerMapper implements RowMapper<customer> {
        public customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            customer customer = new customer();
            customer.setCid(rs.getString("cid"));
            customer.setFirstName(rs.getString("firstname"));
            customer.setLastName((rs.getString("lastname")));
            customer.setBillingAddress(rs.getString("billingaddress"));
            customer.setIsDeleted(rs.getInt("isdeleted"));
            return customer;
        }
    }

    public customer getCustomerById(String id) {
        String sql = "SELECT * FROM customer WHERE cid = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new CustomerMapper());
    }

    // Other CRUD methods
}
