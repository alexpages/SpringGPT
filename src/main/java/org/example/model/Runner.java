package org.example.model;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.config.MessagingConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.*;

@Component
public class Runner {
    //********** ATTRIBUTES **********//
    @Value("${OPENAI_API_KEY}")
    private String OPENAI_API_KEY;
    @Autowired
    private ApplicationEventPublisher responseEvent;
    private RestTemplate template = new RestTemplate();
    private boolean condicion = false;
    @Autowired
    private Response contentResponse;
    private String request;
    private String responseToSend;
    private final RabbitTemplate rabbitTemplate;
    private CustomEvent response = new CustomEvent(this, "response");

    //********** FUNCTIONS **********//
    /**
     * This it the constructor of Runner class, which requires a rabbitTemplate.
     * @param rabbitTemplate It will be used to send information through RabbitMQ messaging broker.
     */
    public Runner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * This class obtain the request from Consumer class sent through RabbitMQ messaging queue.
     * @param message String message received from messaging queue.
     */
    @RabbitListener(queues = "${queue.request}")
    public void receiveRequest (String message) {
        this.request = message;
        condicion = true;
    }

    /**
     * This method sends query to ChatGPT through HTTP and gets the information. After it sends the content of the body to the RabbitMQ queue so Consumer can obtain the information.
     * @param event Which is required for SpringBoot @EventListener
     */
    @EventListener(condition = "event.id == 'request'")
    public synchronized void sendRequest(CustomEvent event) throws IOException {
        //Headers
        if (condicion){
            System.out.println(this.request);
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
            requestBody.put("max_tokens", 300);

            //Send request
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            messageConverters.add(new MappingJackson2HttpMessageConverter());
            template.setMessageConverters(messageConverters);
            ResponseEntity<Object> responseHttp = template.postForEntity(url, request, Object.class);

            //Decode solution to obtain body
            JsonNode jsonResponse = contentResponse.decodeResponse(responseHttp);
            responseToSend = jsonResponse.get("choices").get(0).get("message").get("content").asText();

            //Print response
            rabbitTemplate.convertAndSend(MessagingConfig.TOPICEXCHANGE_NAME, "message.response", responseToSend);

            //Send event
            responseEvent.publishEvent(response);
        }
        condicion=false;

    }
}