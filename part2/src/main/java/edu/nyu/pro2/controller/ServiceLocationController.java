package edu.nyu.pro2.controller;

import edu.nyu.pro2.service.CustomerService;
import edu.nyu.pro2.service.ServiceLocationService;
import edu.nyu.pro2.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicelocation")
@CrossOrigin
public class ServiceLocationController {
    @Autowired
    private ServiceLocationService serviceLocationService;

    @GetMapping("/all")
    public R getAllCids() {
        try {
            List<String> address = serviceLocationService.getAllAddress();

            if (address != null) {
                return R.ok().message("Address retrieved successfully").data("address",address);
            } else {
                return R.error().message("Address not found");
            }
        } catch (Exception e) {
            return R.error().message("Error retrieving address: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getLocationsForUser(@PathVariable String userId) {
        List<String> locations = serviceLocationService.findLocationByUserID(userId); // 逻辑处理
        return ResponseEntity.ok(locations);
    }

}



