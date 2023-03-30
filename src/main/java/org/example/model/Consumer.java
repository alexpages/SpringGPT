package org.example.model;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    //Needs to connect to the queue or the Exchange
    public void receiveMessage (String message){
        System.out.println("Received message:"+message);
    }
}
