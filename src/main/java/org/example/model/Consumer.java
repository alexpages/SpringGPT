package org.example.model;
import org.example.Application;
import org.example.config.MessagingConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitMessageOperations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Consumer {
    private final RabbitTemplate rabbitTemplate;
    @Autowired
    private ApplicationEventPublisher requestEvent;

    //********** FUNCTIONS **********//
    public Consumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    //Needs to connect to the queue or the Exchange
    @RabbitListener(queues = "${queue.response}")
    public void receiveMessage (String message){
        System.out.println("Received message:"+message);
    }

    public String userPrompt(){
        System.out.println("Enter your Request: ");
        Scanner scanner = new Scanner(System.in);
        String userPrompt = scanner.nextLine();
        System.out.println("Received input: " + userPrompt);
        return userPrompt;
    }
    @EventListener(condition = "event.id == 'response'")
    public void sendNewRequest(CustomEvent event){
        rabbitTemplate.convertAndSend(MessagingConfig.TOPICEXCHANGE_NAME, "message.request", this.userPrompt());
        CustomEvent request = new CustomEvent(this, "request");
        requestEvent.publishEvent(request);
        System.out.println("Sending request");
    }
}

