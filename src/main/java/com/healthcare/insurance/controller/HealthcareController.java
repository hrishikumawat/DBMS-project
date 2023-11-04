package com.healthcare.insurance.controller;

import com.healthcare.insurance.dto.ClaimDetailsdto;
import com.healthcare.insurance.dto.CustomerDetails;
import com.healthcare.insurance.dto.Customerdto;
import com.healthcare.insurance.entity.*;
import com.healthcare.insurance.exception.ResourceNotFoundException;
import com.healthcare.insurance.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("healthcare/v1")
public class HealthcareController {

    @Autowired
    private HealthCareRepository healthCareRepository;
    @Autowired
    private HealthCarePolicyRepository healthCarePolicyRepository;
    @Autowired
    private HealthCareClaimRepository healthCareClaimRepository;
    @Autowired
    private HealthCareClaimDetailsRepository healthCareClaimDetailsRepository;
    @Autowired
    private HealthCarePolicyDetailsRepository healthCarePolicyDetailsRepository;

//Create,get,getbyid,update,delete customer
//Create,get,getbyid,delete policy
//Create,get,getbyid,update,delete claim
//
//Customer >Policy(pid,cid) >PolicyDetails
//Customer >Claim(claimid,cid) >ClaimDetails


    //create custoemr
    @PostMapping("/customer/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customerdto request){
        Customer customer = new Customer();
        List< PolicyDetails> policyDetails = new ArrayList<>();
        List<ClaimDetails> claimDetails = new ArrayList<>();

        if(request.getPolicyNumbers().size()>0){
            request.getPolicyNumbers().forEach(policyId ->{
                policyDetails.add(healthCarePolicyDetailsRepository.findById(policyId).stream().findFirst().get());
                // update the customerid in policy table for reference
                Policy policy = new Policy();
                policy.setCid(request.getCid());
                policy.setPid(policyId);
                healthCarePolicyRepository.save(policy);
            });
        }
        if(request.getClaimsNumbers().size()>0){
            request.getClaimsNumbers().forEach(claimId ->{
                Claim claim = new Claim();
                claimDetails.add(healthCareClaimDetailsRepository.findById(claimId).stream().findFirst().get());
                // update the customerid in  claim table for reference
                claim.setCid(request.getCid());
                claim.setClaimid(claimId);
                healthCareClaimRepository.save(claim);
            });
        }
        //finally save customer
        customer.setPno(request.getPno());
        customer.setAddress(request.getAddress());
        customer.setCid(request.getCid());
        customer.setName(request.getName());

        Customer queryRresponse = healthCareRepository.save(customer);

        return ResponseEntity.ok(queryRresponse);
    }
    // create policy rest api
    @PostMapping("/policy/create")
    public PolicyDetails createPolicyDetail(@RequestBody PolicyDetails policyDetails) {
        return healthCarePolicyDetailsRepository.save(policyDetails);
    }
    //create claim
    @PostMapping("/claim/create")
    public ClaimDetails createClaimDetail(@RequestBody ClaimDetails claimDetails){
        return healthCareClaimDetailsRepository.save(claimDetails);
    }
    //create policy
//    @PostMapping("/policy/create")
//    public Policy putPolicy(@RequestBody Customerdto request){
//        Policy policy = new Policy();
//
//        return healthCarePolicyRepository.save(policy);
//    }


    // update customer by id rest api
    @PutMapping("/customer/update")
    public ResponseEntity<Customer> getEmployeeById(@RequestBody Customerdto request) {
        Customer customer = healthCareRepository.findById(request.getCid())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + request.getCid()));
        List<Policy> policies = new ArrayList<>();
        List<Claim> claims = new ArrayList<>();
        // iterate for each policy id in dto and get data from repository of policy and add to customer entity to save in database
        if(request.getPolicyNumbers().size() > 0){
            List<Policy> allPolicies = healthCarePolicyRepository.findAll().stream().filter(thisPolicy->thisPolicy.getCid()== request.getCid()).collect(Collectors.toList());
            allPolicies.forEach(p ->{
                healthCarePolicyRepository.deleteById(p.getId());
            });
            request.getPolicyNumbers().forEach(id -> {
                Policy newPolicy = new Policy();
                newPolicy.setPid(id);
                newPolicy.setCid(request.getCid());
                healthCarePolicyRepository.save(newPolicy);

            });
        }
        // iterate for each claims id in dto and get data from repository of claims and add to customer entity to save in database
        if(request.getClaimsNumbers().size() > 0){
            List<Claim> allClaims = healthCareClaimRepository.findAll().stream().filter(thisClaim ->thisClaim.getCid()== request.getCid()).collect(Collectors.toList());
            allClaims.forEach(c->{
                healthCareClaimRepository.deleteById(c.getId());
            });
            request.getClaimsNumbers().forEach(id -> {

                Claim newClaim = new Claim();
                newClaim.setCid(request.getCid());
                newClaim.setClaimid(id);
                healthCareClaimRepository.save(newClaim);
            });
            //customer.getClaims().addAll(claims);
        }
       // Customer updatedcustomer = healthCareRepository.save(customer);

        return ResponseEntity.ok(customer);
    }


    // get all linked data to customers with policy with claim
    @GetMapping("/customers")
    public List<Customer> findallcustomers(){
        return healthCareRepository.findAll();
    }

    // get all linked data to claims with policy with claim
    @GetMapping("/claims")
    public List<ClaimDetails> findallclaims(){
        return healthCareClaimDetailsRepository.findAll();
    }
    // get all linked data to policies with policy with claim
    @GetMapping("/policies")
    public List<PolicyDetails> findallpolicies(){
        return healthCarePolicyDetailsRepository.findAll();
    }



    // get customer by id rest api
    @GetMapping("/customer/{id}")
    public ResponseEntity<CustomerDetails> getCustomerById(@PathVariable Long id) {
        CustomerDetails details = new CustomerDetails();
        List<ClaimDetails> allClaimDetails = new ArrayList<>();
        List<PolicyDetails> allPolicyDetails = new ArrayList<>();
        Customer customer = healthCareRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist with id :" + id));
        details.setCid(customer.getCid());
        details.setPno(customer.getPno());
        details.setAddress(customer.getAddress());
        details.setName(customer.getName());
        List<Claim> allClaims = healthCareClaimRepository.findAll().stream().filter(thisClaim ->thisClaim.getCid()== id).collect(Collectors.toList());
        allClaims.forEach(claimIid->{
            ClaimDetails cDetail= healthCareClaimDetailsRepository.findById(claimIid.getClaimid()).get();
            allClaimDetails.add(cDetail);
        });
        List<Policy> allPolicies = healthCarePolicyRepository.findAll().stream().filter(thisPolicy->thisPolicy.getCid()== id).collect(Collectors.toList());
        allPolicies.forEach(policyIid->{
            PolicyDetails pDetails = healthCarePolicyDetailsRepository.findById(policyIid.getPid()).get();
            allPolicyDetails.add(pDetails);
        });
        details.setPolicies(allPolicyDetails);
        details.setClaims(allClaimDetails);
        return ResponseEntity.ok(details);
    }
    // get policy by id rest api
    @GetMapping("/policy/{id}")
    public ResponseEntity<PolicyDetails> getPoliciesbyid(@PathVariable Long id) {
        PolicyDetails policy = healthCarePolicyDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not exist with id :" + id));
        return ResponseEntity.ok(policy);
    }
    // get claim by id rest api
    @GetMapping("/claim/{id}")
    public ResponseEntity<ClaimDetails> getClaimDetailsById(@PathVariable Long id) {
        ClaimDetails claim = healthCareClaimDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not exist with id :" + id));
        return ResponseEntity.ok(claim);
    }

    // update claim details
    @PutMapping("/updateclaim/{id}")
    public ResponseEntity<ClaimDetails> updateClaimDetailsById(@PathVariable Long id, @RequestBody ClaimDetailsdto c) {
        ClaimDetails details = healthCareClaimDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        details.setStatus(c.getStatus());

        ClaimDetails updatedClaimdetails = healthCareClaimDetailsRepository.save(details);
        return ResponseEntity.ok(updatedClaimdetails);
    }



        // delete customer by id rest api
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<Boolean> deleteCustomerById(@PathVariable Long id) throws EmptyResultDataAccessException {
        healthCareRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }
    // delete policy by id rest api
    @DeleteMapping("/policy/{id}")
    public ResponseEntity<Boolean> deletePolicyById(@PathVariable Long id) throws EmptyResultDataAccessException {
        healthCarePolicyDetailsRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }
    // delete claim by id rest api
    @DeleteMapping("/claim/{id}")
    public ResponseEntity<Boolean> deleteClaimById(@PathVariable Long id) throws EmptyResultDataAccessException {
        healthCareClaimDetailsRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }

}
