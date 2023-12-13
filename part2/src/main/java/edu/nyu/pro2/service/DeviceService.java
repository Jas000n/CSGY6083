package edu.nyu.pro2.service;

import edu.nyu.pro2.dao.DeviceDao;
import edu.nyu.pro2.dto.DeviceDto;
import edu.nyu.pro2.entity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    public List<DeviceDto> getAllDevices() {
        return deviceDao.findAllDevicesWithDetails();
    }
}
