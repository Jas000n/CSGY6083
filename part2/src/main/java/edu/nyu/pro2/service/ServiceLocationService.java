package edu.nyu.pro2.service;

import edu.nyu.pro2.dao.DeviceTypeDao;
import edu.nyu.pro2.dao.ServiceLocationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceLocationService {

    @Autowired
    private ServiceLocationDao serviceLocationDao;

    public List<String> getAllAddress(){return serviceLocationDao.getAllAddress() ;}

    public List<String> findLocationByUserID(String userID){
        return serviceLocationDao.findLocationsByUserId(userID);
    }

}
