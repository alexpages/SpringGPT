package org.example;
import org.example.model.CustomEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootApplication
public class Application {
    @Autowired
    private ApplicationEventPublisher requestEvent;
    //********** MAIN **********//
    public static void main(String[] args) {
        //Initialize Spring and scan for Beans
        SpringApplication.run(Application.class, args).close();
    }
}