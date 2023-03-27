package org.example;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;


public class RabbitMQConfig {

    ConnectionFactory factory = new ConnectionFactory();

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