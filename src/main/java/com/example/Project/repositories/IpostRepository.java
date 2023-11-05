package com.example.Project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Project.entities.Post;

import java.util.Optional;

public interface IpostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findByName(String name);





}