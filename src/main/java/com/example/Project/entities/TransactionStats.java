package com.example.Project.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class TransactionStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Asset asset; // Reference to the Asset

    private Double lastPrice;

    private Date timestamp;

    // Getters and Setters
    // ...
}
