package com.healthcare.insurance.repository;

import com.healthcare.insurance.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HealthCareClaimRepository extends JpaRepository<Claim, Long> {
    @Modifying
    @Query("delete from Claim c where c.cid=:id")
    List<Claim> removeByCid(@Param("id") Long id);
}
