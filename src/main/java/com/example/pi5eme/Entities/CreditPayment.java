package com.example.pi5eme.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "creditPayment")
public class CreditPayment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cpay")
    private Integer id_cpay;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "start_d")
    private Date start_d;

    @Column(name = "end_d")
    private Date end_d;

    @Column(name = "checkDate")
    private Date checkDate;

    @Column(name= "addition")
    private Float addition;

    @JoinColumn(name = "id_credit", nullable = false)
    @JsonIgnore
    @OneToOne
    private Credit credit;
}
