package com.example.Project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Wallet implements Serializable {

    @Id
    @Column(name="wallet_id", length = 100)
    private String wallet_id;
    @Column(name="balance")
    private float balance= 0.00F;
    @Column(name="coins")
    private int coins=0;
    @Column(name="currency", nullable = false, length = 3)
    private String currency="USD";
    @Column(name="payment_method", nullable = true)
    private String payment_method;
    @Column(name="subscription", nullable = true)
    private String subscription="None";
    @Column(name="dateCreated")
    @CreationTimestamp
    private LocalDateTime dateCreated;
    @Column(name="dateUpdated")
    @UpdateTimestamp
    private LocalDateTime dateUpdated;

    @OneToOne
    private User user;


//  @OneToOne(mappedBy = "userWallet")
//  @JsonIgnore
//  private User user;
////}

}
//public class Wallet implements Serializable {
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @Column(name = "idWallet", length = 255)
//  private Long idWallet;
//  private Double Balance;
//
//
//  @OneToMany(cascade = CascadeType.ALL, mappedBy = "walletBuyer")
//  private Set<Transaction> transactionsBuyer;
//  @OneToMany(cascade = CascadeType.ALL, mappedBy = "walletSeller")
//  private Set<Transaction> transactionsSeller;
//
//
////  @OneToOne(mappedBy = "userWallet")
////  @JsonIgnore
////  private User user;
//////}
//
//}