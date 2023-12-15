package edu.nyu.pro2.service;

import edu.nyu.pro2.dao.DeviceTypeDao;
import edu.nyu.pro2.dao.ServiceLocationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceTypeService {
    @Autowired
    private DeviceTypeDao deviceTypeDao;

    public List<String> getAllName(){return deviceTypeDao.getAllName() ;}

    public List<String> getAllModel(){return deviceTypeDao.getAllModel() ;}

    public List<String> getModelsByType(String type) {

        return deviceTypeDao.getModelsByType(type);
    }

}
