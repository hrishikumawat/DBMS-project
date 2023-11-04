package com.healthcare.insurance.repository;

import com.healthcare.insurance.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface HealthCarePolicyRepository extends JpaRepository<Policy,Long> {

    @Modifying
    @Query(value = "delete from Policy c where c.cid in ?1",nativeQuery = true)
    @Transactional
    void removeByCid(Long id);
}