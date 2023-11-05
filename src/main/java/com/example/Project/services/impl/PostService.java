package com.example.Project.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import  com.example.Project.entities.Post;
import  com.example.Project.repositories.IpostRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {
    private final IpostRepository postRepository;

    public List<Post> retrieveAllPosts() {
        return postRepository.findAll();
    }

    @Transactional
    public Post addOrUpdatePost(Post p) {
        return postRepository.save(p);
    }


    public Post retrievePost(Integer id_post) {
        return postRepository.findById(id_post).orElse(null);
    }

    public void removePost(Integer id_post) {
        postRepository.deleteById(id_post);

    }

    public Optional<Post> retrievePostByName(String name) {
        return postRepository.findByName(name);
    }
}