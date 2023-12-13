package edu.nyu.pro2.controller;

import edu.nyu.pro2.dto.DeviceDto;
import edu.nyu.pro2.entity.Customer;
import edu.nyu.pro2.entity.Device;
import edu.nyu.pro2.service.CustomerService;
import edu.nyu.pro2.service.DeviceService;
import edu.nyu.pro2.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device")
@CrossOrigin
public class DeviceController {
    @Autowired
    private DeviceService deviceService;


    @GetMapping("/all")
    public R getAllDevices() {
        try {
            List<DeviceDto> device = deviceService.getAllDevices();

            if (device != null) {
                return R.ok().message("Devices retrieved successfully").data("device",device);
            } else {
                return R.error().message("Devices not found");
            }
        } catch (Exception e) {
            return R.error().message("Error retrieving device: " + e.getMessage());
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