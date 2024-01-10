package com.example.Project.entities;

import com.example.Project.enums.RoleUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId", nullable = false)
    private Long IdUser;


    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Campaigns> campaigns = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Post> posts = new LinkedHashSet<>();


    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Event> events = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Claim> claims = new LinkedHashSet<>();

    @Column(name = "cin")
    private Integer cin;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "addres")
    private String addres;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "phone")
    private Long Phone;
    @Column(name = "email")
    private String email;

    @Column(name = "user_job")
    private String userJob;

    @Column(name = "user_pwd")
    private String password;

    @NotBlank(message = "Username is required")
    @Size(max = 20)
    @NotNull
    private String  Username;

    private Integer IsVerified;
    private String verificationToken;
    private String verificationCode;
    private Integer Age;
    private Date BirthDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Users_Role",
            joinColumns = @JoinColumn(name = "iduser"),
            inverseJoinColumns = @JoinColumn(name = "idRole"))

    private Set<Role> Roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Wallet wallet;
}


