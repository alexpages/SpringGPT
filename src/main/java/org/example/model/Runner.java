package org.example.model;
import com.rabbitmq.client.Command;
import org.example.config.MessagingConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
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

    //********** ATTRIBUTES **********//
    @Value("${OPENAI_API_KEY}")
    private String OPENAI_API_KEY;
    @Autowired
    private ApplicationEventPublisher responseEvent;

    private String request;
    private final CountDownLatch latch = new CountDownLatch(3);
    private String responseToSend;
    private final RabbitTemplate rabbitTemplate;

    //********** FUNCTIONS **********//
    public Runner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "${queue.request}")
    public void receiveRequest (String message) throws InterruptedException {
        this.request = message;
    }
    @EventListener(condition = "event.id == 'request'")
    public void sendRequest(CustomEvent event) throws InterruptedException {
        //Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); //Establish the body will be in .json format
        headers.setBearerAuth(OPENAI_API_KEY);
        String url = "https://api.openai.com/v1/chat/completions";

        //Build Open AI request
        Map<String, Object> requestBody = new HashMap<>();
        System.out.println("______________________________________");
        System.out.println("Request received: "+this.request);
        System.out.println("______________________________________");
        requestBody.put("model", "gpt-3.5-turbo");
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> message = new HashMap<>();
        messages.add(message);
        message.put("role", "user");
        message.put("content", this.request);
        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.5);
        requestBody.put("max_tokens", 50);

        //Send request
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        RestTemplate template = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        template.setMessageConverters(messageConverters);
        ResponseEntity<Object> responseHttp = template.postForEntity(url, request, Object.class);

        //Print response
        System.out.println("______________________________________");
        this.responseToSend = responseHttp.toString();
        rabbitTemplate.convertAndSend(MessagingConfig.TOPICEXCHANGE_NAME, "message.response", this.responseToSend);
        System.out.println("______________________________________");

        CustomEvent response = new CustomEvent(this, "response");
        responseEvent.publishEvent(response);

    }

}