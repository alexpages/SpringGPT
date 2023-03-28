package org.example.config;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
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
    //tbd
    private final static String QUEUE_NAME = "hello";
    public void send(String message) throws IOException, TimeoutException {
        //Connection to the pre-established host in constructor, in this case
        //localhost
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()){

            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            channel.basicPublish("", QUEUE_NAME,);

        }

}