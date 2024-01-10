package com.example.Project.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountId", nullable = false)
    private long accountId;
@ToString.Exclude
    @OneToMany(mappedBy = "account", orphanRemoval = true)
    private Set<CreditDemand> creditDemands = new LinkedHashSet<>();

    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "balance")
    private Float balance;

    @Column(name = "rib")
    private String rib;


    @OneToMany(mappedBy = "account", orphanRemoval = true)
    private Set<Transaction> transactions = new LinkedHashSet<>();

}