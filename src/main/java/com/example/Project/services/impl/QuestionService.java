package com.example.Project.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import  com.example.Project.entities.Post;
import  com.example.Project.entities.Question;
import  com.example.Project.repositories.IpostRepository;
import  com.example.Project.repositories.IquestionRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionService{
    private final IquestionRepository questionRepository;

    private final IpostRepository postRepository;

    public List<Question> retrieveAllQuestions() {
        return questionRepository.findAll();
    }


    public Question addOrUpdateQuestion(Question q) {
        return questionRepository.save(q);
    }

    public Question retrieveQuestion(Integer id_question) {
        return questionRepository.findById(id_question).orElse(null);
    }

    public List<Question> findByPostIdpost(Integer id_post) {

        return questionRepository.findByPost_PostId(id_post);
    }
    public void removeQuestion(Integer id_question) {
        questionRepository.deleteById(id_question);

    }

    public Question assignQuestionToPost(Integer id_question, Integer id_post) {
        Question q = questionRepository.findById(id_question).orElse(null);
        Post p = postRepository.findById(id_post).orElse(null);
        q.setPost(p);
        return questionRepository.save(q);
    }

}