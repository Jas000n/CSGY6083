package edu.nyu.pro2.controller;

import edu.nyu.pro2.dto.DeviceDto;
import edu.nyu.pro2.entity.Customer;
import edu.nyu.pro2.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import edu.nyu.pro2.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/{id}")
    public R getCustomerById(@PathVariable String id) {
        try {
            Customer customer = customerService.getCustomerById(id);
            if (customer != null) {
                return R.ok().message("Customer retrieved successfully").data("customer", customer);
            } else {
                return R.error().message("Customer not found");
            }
        } catch (Exception e) {
            return R.error().message("Error retrieving customer: " + e.getMessage());
        }
    }


    @GetMapping("/all")
    public R getAllCids() {
        try {
            List<String> cids = customerService.getAllCids();

            if (cids != null) {
                return R.ok().message("cids retrieved successfully").data("cids",cids);
            } else {
                return R.error().message("cids not found");
            }
        } catch (Exception e) {
            return R.error().message("Error retrieving cids: " + e.getMessage());
        }
    }
}
