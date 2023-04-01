package org.example.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OpenAiController implements CommandLineRunner{

    @Value("${OPENAI_API_KEY}")
    private String OPENAI_API_KEY;

    public void sendRequest() throws JsonProcessingException {
        //Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); //Establish the body will be in .json format
        headers.setBearerAuth(OPENAI_API_KEY);
        String url = "https://api.openai.com/v1/chat/completions";

        //First Try
//        String message = "test";
//        RestTemplate restTemplate = new RestTemplate();
//        RestTemplate responseEntity = restTemplate;
//        Request request = new Request();
//        request.setPrompt(message);
//        HttpEntity entity = new HttpEntity<>(request.createBody(),headers);
//        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
//        String result = response.getBody();
//      String response = restTemplate.postForObject(url, entity, String.class);

        //Second Try
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
//        requestBody.put("prompt", "Hello");

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", "Hello!");
        messages.add(message);
        requestBody.put("messages", messages);

        requestBody.put("temperature", 0.5);
        requestBody.put("max_tokens", 50);
        HttpEntity<Map<String, Object>> request2 = new HttpEntity<>(requestBody, headers);

        RestTemplate template = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        template.setMessageConverters(messageConverters);

        //Make Request
        ResponseEntity<String> response = template.postForEntity(url, request2, String.class);
        String result = response.getBody();
        System.out.println(result);

//        try {
//            ResponseEntity<String> response = template.postForEntity(url, request2, String.class);
//            String result = response.getBody();
//            // Handle successful response...
//        } catch (HttpClientErrorException | HttpServerErrorException e) {
//            // Handle HTTP error response...
//            System.err.println("HTTP error status: " + e.getStatusCode());
//            System.err.println("HTTP error response body: " + e.getResponseBodyAsString());
//        } catch (RestClientException e) {
//            // Handle general RestTemplate error...
//            System.err.println("RestTemplate error: " + e.getMessage());
//        }
        //Print Response
//        System.out.println(result);
    }
//
    @Override
    public void run(String... args) throws Exception {
        this.sendRequest();
    }
//    RECEIVES HTTP REQUEST
//
//
//    PUBLISH RESPONSE ON QUEUE2
//

}
