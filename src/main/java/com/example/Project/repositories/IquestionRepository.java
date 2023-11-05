package com.example.Project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.Project.entities.Question;

import java.util.List;
import java.util.Optional;

public interface IquestionRepository extends JpaRepository<Question, Integer> {

    @Query("select q from Question q where q.post.postId = :postId")
    List<Question> findByPost_PostId(Integer postId);

}