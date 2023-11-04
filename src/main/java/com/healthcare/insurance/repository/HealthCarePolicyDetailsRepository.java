package com.healthcare.insurance.repository;

import com.healthcare.insurance.entity.PolicyDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthCarePolicyDetailsRepository extends JpaRepository<PolicyDetails, Long> {
}
