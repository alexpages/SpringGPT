package org.example.config;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootConfiguration
public class RabbitMQConfig {

    ConnectionFactory factory = new ConnectionFactory();

    @Configuration
    public RabbitMQConfig() {
        String userName = "guest";
        String password = "guest";
        String virtualHost = "/";
        String hostName = "localhost";
        Integer portNumber = 5672;

        factory.setUsername(userName);
        factory.setPassword(password);
        factory.setVirtualHost(virtualHost);
        factory.setHost(hostName);
        factory.setPort(portNumber);

    }
}