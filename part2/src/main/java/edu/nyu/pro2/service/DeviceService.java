package edu.nyu.pro2.service;

import edu.nyu.pro2.dao.DeviceDao;
import edu.nyu.pro2.dto.DeviceDto;
import edu.nyu.pro2.entity.Device;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    @Transactional
    public List<DeviceDto> getAllDevices() {
        return deviceDao.findAllDevicesWithDetails();
    }

    @Transactional
    public int addDevice(DeviceDto deviceDto){
        return deviceDao.addDevice(deviceDto);
    }
    @Transactional
    public int updateDevice(String did, DeviceDto deviceDto){
        return deviceDao.updateDevice(did,deviceDto);
    }

    @Transactional
    public DeviceDto findDeviceDetailsById(String did){
        return deviceDao.findDeviceDetailsById(did);
    }

    @Transactional
    public boolean deleteDevice(String did) {
        int rowsAffected = deviceDao.deleteDevice(did);
        return rowsAffected > 0;
    }
    @Transactional
    public List<DeviceDto> findDevicesByUserId(String uid){
        return deviceDao.findDevicesByUserId(uid);
    }



}
