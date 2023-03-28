package org.example;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        //Initialize Spring and scan for Beans
        SpringApplication.run(Application.class, args);

        //Other functions within Main
        System.out.println("Please enter a question");
        Scanner sc= new Scanner(System.in);
        StringBuilder sb = new StringBuilder(sc.nextLine());
        System.out.println(sb);

    }
}