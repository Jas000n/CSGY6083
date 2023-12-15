package edu.nyu.pro2.controller;

import edu.nyu.pro2.dto.DeviceDto;
import edu.nyu.pro2.entity.Customer;
import edu.nyu.pro2.entity.Device;
import edu.nyu.pro2.service.CustomerService;
import edu.nyu.pro2.service.DeviceService;
import edu.nyu.pro2.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("/add")
    public R addDevice(@RequestBody DeviceDto deviceDto) {
        try {
            System.out.println("开始添加");
            int device = deviceService.addDevice(deviceDto);
            return R.ok().message("Device added successfully").data("device", device);
        } catch (Exception e) {
            return R.error().message("Error adding device: " + e.getMessage());
        }
    }

    // 更新现有设备
    @PutMapping("/update/{did}")
    public R updateDevice(@PathVariable String did, @RequestBody DeviceDto deviceDto) {
        try {
            int device = deviceService.updateDevice(did, deviceDto);
            return device != -1 ? R.ok().message("Device updated successfully").data("device", device)
                    : R.error().message("Device not found");
        } catch (Exception e) {
            return R.error().message("Error updating device: " + e.getMessage());
        }
    }

    @GetMapping("/details/{did}")
    public R getDeviceDetails(@PathVariable String did) {
        try {
            DeviceDto deviceDto = deviceService.findDeviceDetailsById(did);
            if (deviceDto != null) {
                return R.ok().message("Device details fetched successfully").data("deviceDetails", deviceDto);
            } else {
                return R.error().message("Device not found");
            }
        } catch (Exception e) {
            return R.error().message("Error fetching device details: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{did}")
    public R deleteDevice(@PathVariable String did) {
        try {
            boolean isDeleted = deviceService.deleteDevice(did);
            return isDeleted ? R.ok().message("Device deleted successfully")
                    : R.error().message("Device not found or could not be deleted");
        } catch (Exception e) {
            return R.error().message("Error deleting device: " + e.getMessage());
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
