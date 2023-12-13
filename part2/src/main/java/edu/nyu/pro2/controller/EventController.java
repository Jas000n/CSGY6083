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

    // 获取特定用户的设备（普通用户用）
//    @GetMapping("/user/{userId}")
//    public R getDevicesByUserId(@PathVariable String userId) {
//        try {
//            List<Device> devices = deviceService.getDevicesByUserId(userId);
//            return R.ok().data("devices", devices);
//        } catch (Exception e) {
//            return R.error().message("Error retrieving devices for user " + userId + ": " + e.getMessage());
//        }
//    }
}
