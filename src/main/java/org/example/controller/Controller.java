package org.example.controller;
import org.example.model.Consumer;
import org.example.model.CustomEvent;
import org.example.model.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;

@org.springframework.stereotype.Controller
public class Controller implements CommandLineRunner{
    @Autowired
    private Runner runner;
    @Autowired
    private Consumer consumer;
    @Autowired
    private ApplicationEventPublisher responseEvent;
    /**
     * Sends first trigger in the system thanks to CommandLineRunner implementation.
     */
    @Override
    public void run(String... args) throws Exception {
        CustomEvent request = new CustomEvent(this, "response");
        consumer.sendNewRequest(request);
    }
}
