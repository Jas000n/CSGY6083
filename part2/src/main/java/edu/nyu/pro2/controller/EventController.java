package edu.nyu.pro2.controller;

import edu.nyu.pro2.dto.EventDto;

import edu.nyu.pro2.service.EventService;
import edu.nyu.pro2.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@CrossOrigin
public class EventController {
    @Autowired
    private EventService eventService;


    @GetMapping("/all")
    public R getAllEvents() {
        try {
            List<EventDto> eventList = eventService.getAllEvents();

            if (eventList != null) {
                return R.ok().message("Event retrieved successfully").data("eventList",eventList);
            } else {
                return R.error().message("Event not found");
            }
        } catch (Exception e) {
            return R.error().message("Error retrieving data: " + e.getMessage());
        }
    }

     //get events details for specific user
    @GetMapping("/user/{userId}")
    public R getEventsByUserId(@PathVariable String userId) {
        try {
            List<EventDto> events = eventService.getEventsByUserID(userId);
            return R.ok().data("eventList", events);
        } catch (Exception e) {
            return R.error().message("Error retrieving events for user " + userId + ": " + e.getMessage());
        }
    }
}
