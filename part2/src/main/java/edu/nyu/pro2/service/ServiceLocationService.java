package edu.nyu.pro2.service;

import edu.nyu.pro2.dao.DeviceTypeDao;
import edu.nyu.pro2.dao.ServiceLocationDao;
import edu.nyu.pro2.entity.ServiceLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceLocationService {

    @Autowired
    private ServiceLocationDao serviceLocationDao;

    public List<String> getAllAddress(){return serviceLocationDao.getAllAddress() ;}

    public List<ServiceLocation> getAllServiceLocations(){
        return serviceLocationDao.getAllServiceLocations();
    }
    public List<ServiceLocation> getAllServiceLocationsByUsers(String userId){
        return serviceLocationDao.getAllServiceLocationsByUsers(userId);
    }


    public List<String> findLocationByUserID(String userID){
        return serviceLocationDao.findLocationsByUserId(userID);
    }

    // 添加新的服务位置
    public int addServiceLocation(String address, String city, String state, String zipcode, int squareFootage, int numberOfBedrooms, int numberOfOccupants, int cid) {
        return serviceLocationDao.addServiceLocation(address, city, state, zipcode, squareFootage, numberOfBedrooms, numberOfOccupants, cid);
    }


    // 更新服务位置
    public int updateServiceLocation(int slid, String address, String city, String state, String zipcode, int squareFootage, int numberOfBedrooms, int numberOfOccupants, int cid) {
        return serviceLocationDao.updateServiceLocation(slid, address, city, state, zipcode, squareFootage, numberOfBedrooms, numberOfOccupants, cid);
    }

    // 删除服务位置
    public int deleteServiceLocation(int slid) {
        return serviceLocationDao.deleteServiceLocation(slid);
    }

}
