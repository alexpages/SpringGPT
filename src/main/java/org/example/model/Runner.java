package org.example.model;
import org.example.config.MessagingConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.concurrent.CountDownLatch;

@Component
public class Runner {

    @Value("${OPENAI_API_KEY}")
    private String OPENAI_API_KEY;
    private String request;
    private final CountDownLatch latch = new CountDownLatch(3);
    private String responseToSend;
    private final RabbitTemplate rabbitTemplate;

    public Runner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "${queue.request}")
    public void receiveRequest (String message) throws InterruptedException {
        this.request = message;
        System.out.println("______________________________________");
        System.out.println("Request received:"+message);
        System.out.println("______________________________________");
    }

    public void sendRequest() throws InterruptedException {
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
        message.put("content", "ESTO ES UNA REQUEST");
        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.5);
        requestBody.put("max_tokens", 50);

        //Send request
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        RestTemplate template = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        template.setMessageConverters(messageConverters);
        ResponseEntity<Object> response = template.postForEntity(url, request, Object.class);

        //Print response
        System.out.println("______________________________________");
        System.out.println(response);
        this.responseToSend = response.toString();
        rabbitTemplate.convertAndSend(MessagingConfig.TOPICEXCHANGE_NAME, "message.response", this.responseToSend);
        System.out.println("______________________________________");
    }
}