package org.example.config;
import org.example.controller.Controller;
import org.example.model.Consumer;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {
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
    public MessageListenerAdapter openAiAdapter(Controller controller){
        return new MessageListenerAdapter(controller, "receiveRequest");
    }

    @Bean
    public SimpleMessageListenerContainer listenerContainerOne(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory, MessageListenerAdapter consumerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_RESPONSE);
        container.setMessageListener(consumerAdapter);
        container.start();
        return container;
    }

    @Bean
    public SimpleMessageListenerContainer listenerContainerTwo(ConnectionFactory connectionFactory, MessageListenerAdapter openAiAdapter ) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_REQUEST);
        container.setMessageListener(openAiAdapter);
        container.start();
        return container;
    }
}