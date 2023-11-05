package com.example.Project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.Project.entities.Response;

import java.util.List;
import java.util.Optional;

public interface IresponseRepository extends JpaRepository<Response, Integer> {

    @Query("select r from Response r where r.question.questionId = :questionId")
    List<Response> findByQuestion_QuestionId(Integer questionId);

}