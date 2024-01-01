package com.example.Project.entities;

import com.example.Project.enums.TypeTransaction;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactionId", nullable = false)
    private Integer transactionId;

    @Column(name = "transaction_amount")
    private Float transactionAmount;

    @Temporal(TemporalType.DATE)
    @Column(name = "transaction_date")
    private Date transactionDate;

    @Enumerated
    @Column(name = "type_transaction")
    private TypeTransaction typeTransaction;
    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccount;


}