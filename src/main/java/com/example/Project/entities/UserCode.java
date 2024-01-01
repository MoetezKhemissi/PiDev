package com.example.Project.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserCode {

    @Id
    @Column(name = "idSms")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSms;
    private String code;
    private String NewPassword;
    private String ConfirmPassword;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;







}
