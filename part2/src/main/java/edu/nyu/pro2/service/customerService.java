package edu.nyu.pro2.service;


import edu.nyu.pro2.entity.customer;
import edu.nyu.pro2.dao.customerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class customerService {

    @Autowired
    private customerDao customerDao;

    public customer getCustomerById(String id) {
        return customerDao.getCustomerById(id);
    }

    // Other business methods
}
