package org.example.controller;
import org.example.Application;
import org.example.model.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Component
@RestController
public class OpenAiController implements CommandLineRunner {

    @Value("${OPENAI_API_KEY}")
    private String OPENAI_API_KEY;

    public void sendRequest(String message){
        //Create request
        Request request = new Request(message);

        String url = "https://api.openai.com/v1/completions";
        RestTemplate restTemplate = new RestTemplate();
        RestTemplate responseEntity = restTemplate;

        //Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); //Establish the body will be in .json format
        headers.set("Authorization", "Bearer"+ OPENAI_API_KEY);

        //Http entity to be posted
        HttpEntity entity = new HttpEntity<>(request.createBody(),headers);

        //Make Request
        String response = restTemplate.postForObject(url, entity, String.class);

        //Print Response
        System.out.println(response);
    }

    @Override
    public void run(String... args) throws Exception {
        this.sendRequest("Hola, que tal");
    }


    //RECEIVES HTTP REQUEST


    //PUBLISH RESPONSE ON QUEUE2


}
