package com.healthcare.insurance.repository;

import com.healthcare.insurance.entity.ClaimDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthCareClaimDetailsRepository extends JpaRepository<ClaimDetails,Long> {
}
