package com.example.Project.services.impl;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import  com.example.Project.entities.Question;
import  com.example.Project.entities.Response;
import  com.example.Project.repositories.IquestionRepository;
import  com.example.Project.repositories.IresponseRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ResponsesService  {
    private final IresponseRepository responseRepository;
    private final IquestionRepository questionRepository;


    public List<Response> retrieveAllResponses() {
        return responseRepository.findAll();
    }


    public Response addOrUpdateResponse(Response r) {
        return responseRepository.save(r);
    }


    public Response retrieveResponse(Integer id_response) {
        return responseRepository.findById(id_response).orElse(null);
    }

    public void removeResponse(Integer id_response) {
        responseRepository.deleteById(id_response);
    }

    public Response assignResponseToQuestion(Integer id_response, Integer id_question) {
        Response r = responseRepository.findById(id_response).orElse(null);
        Question q = questionRepository.findById(id_question).orElse(null);
        r.setQuestion(q);
        return responseRepository.save(r);
    }

    public List<Response> findByQuestionIdQuestion(Integer id_question) {
        return responseRepository.findByQuestion_QuestionId(id_question);
    }
}