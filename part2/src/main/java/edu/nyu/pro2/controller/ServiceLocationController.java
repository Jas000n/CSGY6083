package edu.nyu.pro2.controller;

import edu.nyu.pro2.entity.ServiceLocation;
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
    public R getAllAddresses() {
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

    @GetMapping("/alllocations")
    public ResponseEntity<R> getAllServiceLocations() {
        try {
            List<ServiceLocation> locations = serviceLocationService.getAllServiceLocations();
            if (!locations.isEmpty()) {
                return ResponseEntity.ok(R.ok().data("locations", locations));
            } else {
                return ResponseEntity.ok(R.error().message("No locations found"));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(R.error().message("Error retrieving locations: " + e.getMessage()));
        }
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getLocationsForUser(@PathVariable String userId) {
        List<String> locations = serviceLocationService.findLocationByUserID(userId); // 逻辑处理
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/userlocation/{userId}")
    public ResponseEntity<R> getAllServiceLocationsByUsers(@PathVariable String userId) {
        try {
            List<ServiceLocation> locations = serviceLocationService.getAllServiceLocationsByUsers(userId);
            if (!locations.isEmpty()) {
                return ResponseEntity.ok(R.ok().data("locations", locations));
            } else {
                return ResponseEntity.ok(R.error().message("No locations found"));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(R.error().message("Error retrieving locations: " + e.getMessage()));
        }
    }


    // 添加新的服务位置
    @PutMapping("/add")
    public ResponseEntity<R> addServiceLocation(@RequestBody ServiceLocation serviceLocation) {
        int result = serviceLocationService.addServiceLocation(serviceLocation.getAddress(), serviceLocation.getCity(), serviceLocation.getState(), serviceLocation.getZipcode(), Integer.parseInt(serviceLocation.getSquareFootage()), Integer.parseInt(serviceLocation.getNumberOfBedrooms()), Integer.parseInt(serviceLocation.getNumberOfOccupants()), Integer.parseInt(serviceLocation.getCid()));
        return result > 0 ? ResponseEntity.ok(R.ok().message("Service location added successfully")) : ResponseEntity.ok(R.error().message("Failed to add service location"));
    }

    // 更新服务位置
    @PutMapping("/update/{slid}")
    public ResponseEntity<R> updateServiceLocation(@PathVariable String slid, @RequestBody ServiceLocation serviceLocation) {
        int result = serviceLocationService.updateServiceLocation(Integer.parseInt(slid), serviceLocation.getAddress(), serviceLocation.getCity(), serviceLocation.getState(), serviceLocation.getZipcode(), Integer.parseInt(serviceLocation.getSquareFootage()), Integer.parseInt(serviceLocation.getNumberOfBedrooms()), Integer.parseInt(serviceLocation.getNumberOfOccupants()), Integer.parseInt(serviceLocation.getCid()));
        return result > 0 ? ResponseEntity.ok(R.ok().message("Service location updated successfully")) : ResponseEntity.ok(R.error().message("Failed to update service location"));
    }

    // 删除服务位置
    @DeleteMapping("/delete/{slid}")
    public ResponseEntity<R> deleteServiceLocation(@PathVariable String slid) {
        int result = serviceLocationService.deleteServiceLocation(Integer.parseInt(slid));
        return result > 0 ? ResponseEntity.ok(R.ok().message("Service location deleted successfully")) : ResponseEntity.ok(R.error().message("Failed to delete service location"));
    }



}



