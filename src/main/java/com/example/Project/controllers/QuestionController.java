package com.example.Project.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.Project.entities.Question;
import com.example.Project.services.impl.QuestionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
@CrossOrigin(origins = "*")

public class QuestionController {
    private final QuestionService questionServices  ;
    @PostMapping("/add")
    Question addQuestion(@RequestBody Question question ){
        return questionServices.addOrUpdateQuestion(question);
    }


    @GetMapping("/get/{id}")
    Question getQuestion(@PathVariable("id") Integer id_question){
        return questionServices.retrieveQuestion(id_question);
    }
    @GetMapping("/get/questions/{id_post}")
    List<Question> getQuestions(@PathVariable("id_post") Integer id_post){
        return questionServices.findByPostIdpost(id_post);
    }
    @GetMapping("/all")
    List<Question> getAllQuestions(){
        return questionServices.retrieveAllQuestions();
    }
    @DeleteMapping("/delete/{id}")
    void deleteQuestion(@PathVariable("id") Integer id_question){
        questionServices.removeQuestion(id_question);
    }

    @PutMapping("/assignQuestionToPost/{id_question}/{id_post}")
    public Question assignQuestionToPost(@PathVariable("id_question") Integer id_question,
                                                 @PathVariable("id_post") Integer id_post){
        return questionServices.assignQuestionToPost(id_question,id_post);
    }
}
