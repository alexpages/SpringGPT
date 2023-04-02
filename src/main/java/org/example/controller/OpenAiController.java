package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.sql.Array;
import java.util.*;

@Component
@Controller
public class OpenAiController implements CommandLineRunner{

    @Value("${OPENAI_API_KEY}")
    private String OPENAI_API_KEY;
    private String request;
    private Boolean keepAlive = true;

    @RabbitListener(queues = "${queue.request}")
    public void receiveRequest (String message){
        System.out.println("Request received:"+message);
    }

    public void sendRequest() throws JsonProcessingException {
        //Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); //Establish the body will be in .json format
        headers.setBearerAuth(OPENAI_API_KEY);
        String url = "https://api.openai.com/v1/chat/completions";

        //Build Open AI request
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> message = new HashMap<>();
        messages.add(message);

        message.put("role", "user");
        message.put("content", this.request);
        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.5);
        requestBody.put("max_tokens", 50);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        RestTemplate template = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        template.setMessageConverters(messageConverters);

        //Make Request
        ResponseEntity<Object> response = template.postForEntity(url, request, Object.class);
        System.out.println("______________________________________");
        System.out.println(response);
        System.out.println("______________________________________");
    }

    @Override
    public void run(String... args) throws Exception {
        this.sendRequest();
    }
}
