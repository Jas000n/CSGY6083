package edu.nyu.pro2.service;

import edu.nyu.pro2.dao.DeviceTypeDao;
import edu.nyu.pro2.dao.EnergyPriceDao;
import edu.nyu.pro2.entity.EnergyPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnergyPriceService {

    @Autowired
    private EnergyPriceDao energyPriceDao;

    public List<EnergyPrice> findAll(){
        return energyPriceDao.findAll();
    }
}
