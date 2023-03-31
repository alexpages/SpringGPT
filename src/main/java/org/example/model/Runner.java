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

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Consumer consumer;

    public Runner(RabbitTemplate rabbitTemplate, Consumer consumer) {
        this.rabbitTemplate = rabbitTemplate;
        this.consumer = consumer;
    }

    @Override
    public void run(String... args) throws Exception {
//        OpenAiController openAiController = new OpenAiController();
//        openAiController.sendRequest("Hola");
        System.out.println("Sending a message");
        String message = "MESSAGE INCOMING:";
        rabbitTemplate.convertAndSend(Application.TOPICEXCHANGE_NAME, "foo.bar.baz",message);
    }
}
