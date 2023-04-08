package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootApplication
public class Application {
    @Autowired
    private ApplicationEventPublisher requestEvent;
    //********** MAIN **********//
    /**
     * Runs Spring Boot framework and gathers all information to build the app.
     */
    public static void main(String[] args) {
        //Initialize Spring and scan for Beans
        SpringApplication.run(Application.class, args).close();
    }
}