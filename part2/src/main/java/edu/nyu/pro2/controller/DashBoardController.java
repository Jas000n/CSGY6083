package edu.nyu.pro2.controller;

import edu.nyu.pro2.dto.DeviceDto;
import edu.nyu.pro2.dto.EnergyConsumptionDTO;
import edu.nyu.pro2.service.DashBoardService;
import edu.nyu.pro2.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin
public class DashBoardController {

    @Autowired
    private DashBoardService dashBoardService;

    @GetMapping("PreviousConsumptionID/{cid}")
    public R getLastDayConsumptionByID(@PathVariable String cid){
        try {
            List<EnergyConsumptionDTO> DTO = dashBoardService.getLastDayConsumptionByCid(cid);
            if (DTO != null) {
                return R.ok().message("fetched successfully").data("EnergyConsumption", DTO);
            } else {
                return R.error().message("EnergyConsumption not found");
            }
        } catch (Exception e) {
            return R.error().message("Error fetching energyConsumption details: " + e.getMessage());
        }
    }
}
