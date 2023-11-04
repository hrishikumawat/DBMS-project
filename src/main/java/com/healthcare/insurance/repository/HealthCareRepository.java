package com.healthcare.insurance.repository;

import com.healthcare.insurance.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthCareRepository extends JpaRepository<Customer,Long> {
    //@Query("Select u from customer c")
    //Customer findAllCustomer();

}
