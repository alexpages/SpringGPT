package org.example.model;
import org.example.config.MessagingConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
    private boolean condicion = true;

    //********** FUNCTIONS **********//
    /**
     * This it the constructor of Consumer class, which requires a rabbitTemplate.
     * @param rabbitTemplate It will be used to send information through RabbitMQ messaging broker.
     */
    public Consumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * This class obtain the messages that Runner sends through RabbitMQ messaging queue.
     * @param message String message received from messaging queue.
     */
    @RabbitListener(queues = "${queue.response}")
    public void receiveMessage (String message){
        System.out.println("Received message: "+message);
        System.out.println("______________________________________");
        condicion=true;
    }
    /**
     * This method sends a request to the Runner class with the user input from keyboard.
     * @param event Which is required for SpringBoot @EventListener
     */
    @EventListener(condition = "event.id == 'response'")
    public void  sendNewRequest(CustomEvent event){
           if (condicion == true){
               System.out.println("______________________________________");
               System.out.println("Enter your Request: ");
               userPrompt = scanner.nextLine();
               rabbitTemplate.convertAndSend(MessagingConfig.TOPICEXCHANGE_NAME, "message.request", userPrompt);
               System.out.println("Sending request");

               requestEvent.publishEvent(request);
           }
           condicion = false;

    }
}

