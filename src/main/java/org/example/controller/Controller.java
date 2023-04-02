package org.example.controller;
import org.example.model.Consumer;
import org.example.model.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;

@org.springframework.stereotype.Controller
public class Controller implements CommandLineRunner{

    @Autowired
    private Runner runner;
    @Autowired
    private Consumer consumer;

    private final CountDownLatch latch = new CountDownLatch(3);

    @Override
    public void run(String... args) throws Exception {
        this.consumer.sendNewRequest();
        this.runner.sendRequest();
    }
}
