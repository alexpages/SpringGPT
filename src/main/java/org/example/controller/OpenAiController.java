package org.example.controller;
import org.example.Application;
import org.example.model.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OpenAiController implements CommandLineRunner{

    @Value("${OPENAI_API_KEY}")
    private String OPENAI_API_KEY;

    public void sendRequest(){
        //Create request
        String message = "test";
        Request request = new Request();
        request.setPrompt(message);
        RestTemplate restTemplate = new RestTemplate();
        RestTemplate responseEntity = restTemplate;

        //Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); //Establish the body will be in .json format
        headers.setBearerAuth(OPENAI_API_KEY);
        String url = "https://api.openai.com/v1/completions";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "davinci");
        requestBody.put("prompt", "Hello,");
        requestBody.put("temperature", 0.5);
        requestBody.put("max_tokens", 50);

                //Http entity to be posted
//        HttpEntity entity = new HttpEntity<>(request.createBody(),headers);

        HttpEntity<Map<String, Object>> request2 = new HttpEntity<>(requestBody, headers);

        RestTemplate tmplate = new RestTemplate();

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        tmplate.setMessageConverters(messageConverters);

        ResponseEntity<String> response = tmplate.postForEntity(url, request2, String.class);
        String result = response.getBody();

        //Make Request
//      String response = restTemplate.postForObject(url, entity, String.class);

        //Print Response
        System.out.println(result);
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
