package org.example.model;
import jakarta.annotation.PostConstruct;
import org.example.config.MessagingConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Consumer {
    //********** ATTRIBUTES **********//
    @Autowired
    private ApplicationEventPublisher requestEvent;
    private String userPrompt;
    private final RabbitTemplate rabbitTemplate;
    private Scanner scanner = new Scanner(System.in);
    private CustomEvent request = new CustomEvent(this, "request");

    //********** FUNCTIONS **********//
    public Consumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "${queue.response}")
    public void receiveMessage (String message){
        System.out.println("Received message: "+message);
        System.out.println("______________________________________");
    }

    @EventListener(condition = "event.id == 'response'")
    public void  sendNewRequest(CustomEvent event){
            System.out.println("______________________________________");
            System.out.println("Enter your Request: ");
            userPrompt = scanner.nextLine();
            rabbitTemplate.convertAndSend(MessagingConfig.TOPICEXCHANGE_NAME, "message.request", userPrompt);
            System.out.println("Sending request");
            requestEvent.publishEvent(request);
    }
}

