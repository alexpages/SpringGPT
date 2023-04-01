package org.example.model;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    //Needs to connect to the queue or the Exchange
    @RabbitListener(queues = "${queue.response}")
    public void receiveMessage (String message){
        System.out.println("Received message:"+message);
    }
}
