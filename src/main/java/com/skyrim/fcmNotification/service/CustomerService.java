package com.skyrim.fcmNotification.service;

import com.skyrim.fcmNotification.Repository.CustomerRepository;
import com.skyrim.fcmNotification.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(Long customerId)
    {
        return customerRepository.findById(customerId);
    }

}

