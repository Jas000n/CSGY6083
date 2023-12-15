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

    public int addDevice(DeviceDto deviceDto){
        return deviceDao.addDevice(deviceDto);
    }

    public int updateDevice(String did, DeviceDto deviceDto){
        return deviceDao.updateDevice(did,deviceDto);
    }

    public DeviceDto findDeviceDetailsById(String did){
        return deviceDao.findDeviceDetailsById(did);
    }


    public boolean deleteDevice(String did) {
        int rowsAffected = deviceDao.deleteDevice(did);
        return rowsAffected > 0;
    }




}
