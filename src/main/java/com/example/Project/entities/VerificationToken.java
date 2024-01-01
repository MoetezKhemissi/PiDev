package com.example.Project.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken implements Serializable {
    @Id
    @Column(name = "idVerif")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVerif;
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;


    private LocalDateTime expiryDate;

}