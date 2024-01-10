package com.example.Project.controllers;

import com.example.Project.DTO.ChatGPTRequest;
import com.example.Project.DTO.ChatGptResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bot")
@CrossOrigin(origins = "*")
public class CustomBotController {

    @Value("${openai.api.key}")
    String openaiApiKey;
    @Value("${openai.model}")
    private String model;

    @Value(("${openai.api.url}"))
    private String apiURL;

    @Autowired
    private RestTemplate template;

    @GetMapping("/chat")
    public String chat(@RequestParam("prompt") String prompt) {
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        System.out.println(prompt);
        return chatGptResponse.getChoices().get(0).getMessage().getContent();
    }
}