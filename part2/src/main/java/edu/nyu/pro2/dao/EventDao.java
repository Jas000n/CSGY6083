package edu.nyu.pro2.dao;

import edu.nyu.pro2.dto.DeviceDto;
import edu.nyu.pro2.dto.EventDto;
import edu.nyu.pro2.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EventDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class EventMapper implements RowMapper<EventDto> {
        public EventDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            EventDto eventDto = new EventDto();
            eventDto.setEventID(rs.getString("eid"));
            eventDto.setEventLocation(rs.getString("address"));
            eventDto.setEventDeviceModel(rs.getString("name"));
            eventDto.setEventDevice(rs.getString("model"));
            eventDto.setEventType(rs.getString("label"));
            eventDto.setEventValue1(rs.getString("value1"));
            eventDto.setEventValue2(rs.getString("value2"));
            eventDto.setEventTime(rs.getString("timestamp"));
            return eventDto;
        }
    }


    public List<EventDto> findAllEventsWithDetails() {
        String sql = "SELECT e.eid, sl.address, dt.name,dt.model, e.label, e.value1, e.value2, e.timestamp " +
                "FROM Event E " +
                "JOIN device d on D.did = E.did "+
                "JOIN devicetype dt ON d.dtid = dt.dtid " +
                "JOIN servicelocation sl ON d.slid = sl.slid " +
                "WHERE d.isDeleted = 0";
        return jdbcTemplate.query(sql, new Object[]{}, new EventMapper());


    }
    public List<EventDto> getEventsByUserID(String ID){
        String sql = "SELECT e.eid, sl.address, dt.name,dt.model, e.label, e.value1, e.value2, e.timestamp " +
                "FROM Event E " +
                "JOIN device d on D.did = E.did "+
                "JOIN devicetype dt ON d.dtid = dt.dtid " +
                "JOIN servicelocation sl ON d.slid = sl.slid " +
                "WHERE d.isDeleted = 0 and sl.cid = ?";
        return jdbcTemplate.query(sql, new Object[]{ID}, new EventMapper());
    }

}
