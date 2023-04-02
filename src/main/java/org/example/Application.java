package org.example;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    //********** MAIN **********//
    public static void main(String[] args) {
        //Initialize Spring and scan for Beans
        SpringApplication.run(Application.class, args).close();
    }
}