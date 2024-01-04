package com.example.pi5eme.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Credit")
public class Credit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_credit")
    private Integer id_credit;

    @Column(name = "amount")
    private Float amount;



    @Column(name = "start_d")
    private Date start_d;

    @Column(name = "end_d")
    private Date end_d;

    @Column(name = "ratio")
    private Float ratio;

    @Column(name = "period")
    private Integer period;  //by months

    @Column(name = "next_pay")
    private Date next_pay;

    @Column(name = "rest")
    private Float rest;

    @Column(name = "pay_every")
    private Integer pay_every; //by months

    @Column(name = "next_bill")
    private Float next_bill;


    @Column(name = "final_payment")
    private Float final_payment;



    @ManyToOne
    @JoinColumn(name = "credit_analysis_id")
    @JsonIgnore
    private CreditAnalysis creditAnalysis;

    @OneToOne(mappedBy = "credit", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private CreditPayment creditPayment;
}
