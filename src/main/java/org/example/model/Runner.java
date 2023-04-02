package org.example.model;

import com.rabbitmq.client.AMQP;
import org.example.Application;
//import org.example.controller.OpenAiController;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Consumer consumer;

    public Runner(RabbitTemplate rabbitTemplate, Consumer consumer) {
        this.rabbitTemplate = rabbitTemplate;
        this.consumer = consumer;
    }

    public String userPrompt(){
        System.out.println("Enter your response: \n");
        Scanner scanner = new Scanner(System.in);
        String userPrompt = scanner.nextLine();
        return userPrompt;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending a message");
        rabbitTemplate.convertAndSend(Application.TOPICEXCHANGE_NAME, "message.response", this.userPrompt());
    }
}
