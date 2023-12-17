package edu.nyu.pro2.controller;

import edu.nyu.pro2.dao.EnergyPriceDao;
import edu.nyu.pro2.dto.EnergyPriceDTO;
import edu.nyu.pro2.entity.EnergyPrice;
import edu.nyu.pro2.service.EnergyPriceService;
import edu.nyu.pro2.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/price")
@CrossOrigin
public class EnergyPriceController {
    @Autowired
    private EnergyPriceService energyPriceService;

    @GetMapping("/all")
    public R getAllPrices() {
        try {
            List<EnergyPrice> prices = energyPriceService.findAll();

            if (!prices.isEmpty()) {
                return R.ok().message("Prices retrieved successfully").data("prices", prices);
            } else {
                return R.error().message("No prices found");
            }
        } catch (Exception e) {
            return R.error().message("Error retrieving prices: " + e.getMessage());
        }
    }

    @GetMapping("/getRecent")
    public R getRecent(){
        try {
            List<EnergyPriceDTO> prices = energyPriceService.findLast7DayData();

            if (!prices.isEmpty()) {
                return R.ok().message("Prices retrieved successfully").data("prices", prices);
            } else {
                return R.error().message("No prices found");
            }
        } catch (Exception e) {
            return R.error().message("Error retrieving prices: " + e.getMessage());
        }
    }

}
