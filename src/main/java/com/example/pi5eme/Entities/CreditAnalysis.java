package com.example.pi5eme.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CreditAnalysis")
public class CreditAnalysis implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_analysis")
    private Integer idAnalysis;

    @Column(name = "mv_score")
    private Float MvScore;  // A score representing the creditworthiness of the user

    @Column(name = "total_credit_limit")
    private Float totalCreditLimit; // The total amount of credit the user is eligible for

    @Column(name = "interest_rate")
    private Double interestRate; // Average or offered interest rate

    @Column(name = "credit_risk_level")
    private String creditRiskLevel;

    @Column(name = "debt_to_income_ratio")
    private Float debtToIncomeRatio;




    // Relationship with User
    @OneToOne
    @JoinColumn(name = "accountId", nullable = false)
    @JsonIgnore
    private Account account;

   @OneToMany(mappedBy = "creditAnalysis")
   @JsonIgnore
    private Set<Credit> credits;

    // ... other properties and methods ...
}
