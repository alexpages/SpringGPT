package org.example;
import org.example.controller.OpenAiController;
import org.example.model.Consumer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationProperties
public class Application {
    @Value("${SPRINGBOOT_USER_NAME}")
    private String userName;
    @Value("${SPRINGBOOT_PASSWORD}")
    private String password;
    @Value("${SPRINGBOOT_VIRTUALHOST}")
    private String virtualHost;
    @Value("${SPRINGBOOT_HOST}")
    private String hostName;
    @Value("${SPRINGBOOT_PORT}")
    private Integer portNumber;
    @Value("${queue.request}")
    private String QUEUE_REQUEST;
    @Value("${queue.response}")
    private String QUEUE_RESPONSE;
    public final static String TOPICEXCHANGE_NAME = "sb-exchange";

    @Bean
    public Queue requestQueue(){
        return new Queue(this.QUEUE_REQUEST);
    }
    @Bean
    public Queue responseQueue(){
        return new Queue(this.QUEUE_RESPONSE);
    }
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(this.TOPICEXCHANGE_NAME);
    }
    @Bean
    public Binding bindingRequest(Queue requestQueue, TopicExchange exchange){
        return BindingBuilder.bind(requestQueue).to(exchange).with("message.request");
    }
    @Bean
    public Binding bindingResponse(Queue responseQueue, TopicExchange exchange){
        return BindingBuilder.bind(responseQueue).to(exchange).with("message.response");
    }
    @Bean
    public MessageListenerAdapter consumerAdapter(Consumer consumer){
        return new MessageListenerAdapter(consumer, "receiveMessage");
    }
    @Bean
    public MessageListenerAdapter openAiAdapter(OpenAiController openAIController){
        return new MessageListenerAdapter(openAIController, "receiveRequest");
    }

    @Bean
    public SimpleMessageListenerContainer listenerContainerOne(ConnectionFactory connectionFactory, MessageListenerAdapter consumerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_REQUEST);
        container.setMessageListener(consumerAdapter);
        container.start();
        return container;
    }

    @Bean
    public SimpleMessageListenerContainer listenerContainerTwo(ConnectionFactory connectionFactory,MessageListenerAdapter openAiAdapter ) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_RESPONSE);
        container.setMessageListener(openAiAdapter);
        container.start();
        return container;
    }

    //********** MAIN **********//
    public static void main(String[] args) {
        //Initialize Spring and scan for Beans
        SpringApplication.run(Application.class, args).close();
    }
}