package com.healthcare.insurance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "PolicyDetails")
public class PolicyDetails {
    @Id
    private long pid;
    private String ptype;
    private String benefits;
    private long suminsured;
    private long premium;
    private long tenure;

}
