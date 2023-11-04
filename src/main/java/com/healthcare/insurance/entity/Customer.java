package com.healthcare.insurance.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "Customer")
public class Customer {
    @Id
    @Column(name = "cid")
    private long cid;

    private String name;

    private String address;

    @Column(name = "pno")
    private long pno;
}
