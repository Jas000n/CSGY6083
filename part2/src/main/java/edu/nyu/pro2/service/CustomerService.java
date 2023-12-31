package edu.nyu.pro2.service;


import edu.nyu.pro2.entity.Customer;
import edu.nyu.pro2.dao.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.List;


@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public Customer getCustomerById(String id) {
        return customerDao.getCustomerById(id);
    }


    public List<String> getAllCids(){return customerDao.getAllCids();}


    // Other business methods
}
