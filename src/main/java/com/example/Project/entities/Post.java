package com.example.Project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId", nullable = false)
    private Integer postId;
    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private Set<Question> questions = new LinkedHashSet<>();

    @Column(name = "review")
    private String review;
    private String type;
    private String name;

    @Column(name = "text")
    private String text;
    @Column(name = "image", unique = false, nullable = false, length = 100000)
    private byte[] image;
    @Column(name = "status")
    private Boolean status;


    @ManyToOne
    @JoinColumn(name = "user_user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return getPostId() != null && Objects.equals(getPostId(), post.getPostId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}