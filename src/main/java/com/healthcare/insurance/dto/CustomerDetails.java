package com.healthcare.insurance.dto;

import com.healthcare.insurance.entity.ClaimDetails;
import com.healthcare.insurance.entity.PolicyDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDetails {

    private long cid;

    private String name;

    private String address;

    private List<PolicyDetails> policies;

    private List<ClaimDetails> claims;

    private long pno;
}
