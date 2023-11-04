package com.healthcare.insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customerdto {

        private long cid;

        private String name;

        private String address;

        private List<Long> policyNumbers;

        private List<Long> claimsNumbers;

        private long pno;

}
