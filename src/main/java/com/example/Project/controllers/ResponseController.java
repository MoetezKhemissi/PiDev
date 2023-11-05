package com.example.Project.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.Project.entities.Response;
import com.example.Project.services.impl.ResponsesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/response")
@CrossOrigin(origins = "*")
public class ResponseController {
    private final ResponsesService responseServices;


    @PostMapping("/add")
    Response addResponse(@RequestBody Response response ){
        return responseServices.addOrUpdateResponse(response);
    }

    @GetMapping("/get/{id}")
    Response getResponse(@PathVariable("id") Integer id_response){
        return responseServices.retrieveResponse(id_response);
    }
    @GetMapping("/all")
    List<Response> getAllResponses(){
        return responseServices.retrieveAllResponses();
    }
    @DeleteMapping("/delete/{id}")
    void deleteResponse(@PathVariable("id") Integer id_response){
        responseServices.removeResponse(id_response);
    }

    @PutMapping("/assignResponseToQuestion/{id_response}/{id_question}")
    public Response assignResponseToQuestion(@PathVariable("id_response") Integer id_response,
                                             @PathVariable("id_question") Integer id_question){
        return responseServices.assignResponseToQuestion(id_response,id_question);
    }
    @GetMapping("/get/responses/{id_question}")
    List<Response> getQuestions(@PathVariable("id_question") Integer id_question){
        return responseServices.findByQuestionIdQuestion(id_question);
    }

}
