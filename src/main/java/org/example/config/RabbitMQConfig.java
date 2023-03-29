package org.example.config;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private String userName = "guest";
    private String password = "guest";
    private String virtualHost = "/";
    private String hostName = "localhost";
    private Integer portNumber = 5672;
    private final static String QUEUE_NAME = "hello";

    @Bean
    public ConnectionFactory factory(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(userName);
        factory.setPassword(password);
        factory.setVirtualHost(virtualHost);
        factory.setHost(hostName);
        factory.setPort(portNumber);
        return factory;
    }
    @Bean
    public Queue messagingQueue(){
        return new Queue(this.QUEUE_NAME);
    }

}