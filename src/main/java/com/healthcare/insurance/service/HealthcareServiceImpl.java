package com.healthcare.insurance.service;

import com.healthcare.insurance.entity.Claim;
import com.healthcare.insurance.entity.Customer;
import com.healthcare.insurance.entity.Policy;
import com.healthcare.insurance.repository.HealthCareClaimRepository;
import com.healthcare.insurance.repository.HealthCarePolicyRepository;
import com.healthcare.insurance.repository.HealthCareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthcareServiceImpl implements HealthcareService {
    @Autowired
    private HealthCareRepository healthCareRepository;
    private HealthCareClaimRepository healthCareClaimRepository;
    private HealthCarePolicyRepository healthCarePolicyRepository;
    @Override
    public List<Customer> getAllCustomers() {

        return healthCareRepository.findAll();
    }
    @Override
    public List<Claim> getAllClaims() {

        return healthCareClaimRepository.findAll();
    }

    @Override
    public List<Policy> getAllPolicy() {
        return healthCarePolicyRepository.findAll();
    }

    @Override
    public Customer getCustomerDetails(Long customerId) {
        return healthCareRepository.findById(customerId).stream().findFirst().orElse(null);
    }
    // other CRUD operations
}