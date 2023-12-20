package edu.nyu.pro2.service;

import edu.nyu.pro2.dao.EnergyPriceDao;
import edu.nyu.pro2.dto.EnergyPriceDTO;
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

    public List<EnergyPriceDTO> findLast7DayData(){
        return energyPriceDao.findLast7Day();
    }

    public List<EnergyPriceDTO> findLast7DayDataByID(String cid){
        return energyPriceDao.findLast7DayByID(cid);
    }
}
