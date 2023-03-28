package org.example.model;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class Sender {
    @Autowired
    private RabbitTemplate template;
    @Autowired
    private Queue queue;

    public void send(String message){
        this.template.convertAndSend(queue.getName(), message);
        System.out.println("Message has been sent");
    }

}
