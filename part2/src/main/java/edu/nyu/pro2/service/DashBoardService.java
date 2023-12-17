package edu.nyu.pro2.service;

import edu.nyu.pro2.dao.DashBoardDao;
import edu.nyu.pro2.dto.EnergyConsumptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashBoardService {
    @Autowired
    private DashBoardDao dashBoardDao;

    public List<EnergyConsumptionDTO> getLastDayConsumptionByCid(String cid) {
        return dashBoardDao.getAllEnergyConsumption(cid);
    }
}
