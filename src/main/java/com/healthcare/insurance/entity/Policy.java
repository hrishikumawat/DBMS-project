package com.healthcare.insurance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Policy")
public class Policy {
    @Id
    @GeneratedValue
    private long id;
    private long pid;
    private long cid;
}
