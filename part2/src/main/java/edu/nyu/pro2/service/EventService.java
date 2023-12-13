package edu.nyu.pro2.service;

import edu.nyu.pro2.dao.EventDao;
import edu.nyu.pro2.dto.EventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventDao eventDao;

    public List<EventDto> getAllEvents() {
        return eventDao.findAllEventsWithDetails();
    }
    public List<EventDto> getEventsByUserID(String ID){
        return eventDao.getEventsByUserID(ID);}
}
