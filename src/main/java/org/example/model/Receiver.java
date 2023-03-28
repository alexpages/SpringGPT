package org.example.model;

import org.springframework.stereotype.Component;

@Component
public class Receiver {

    //CountDownLatch Latch?
    public void receiveMessage (String message){
        System.out.println("received<" +message +">");
    }
}
