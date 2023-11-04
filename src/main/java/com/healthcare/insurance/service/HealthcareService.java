package com.healthcare.insurance.service;

import com.healthcare.insurance.entity.Claim;
import com.healthcare.insurance.entity.Customer;
import com.healthcare.insurance.entity.Policy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HealthcareService  {
    List<Customer> getAllCustomers();

    List<Claim> getAllClaims();

    List<Policy> getAllPolicy();

    Customer getCustomerDetails(Long customerId);
}
