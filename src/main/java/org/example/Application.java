package org.example;
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
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ConfigurationProperties
public class Application {

    private String userName = "guest";
    private String password = "guest";
    private String virtualHost = "/";
    @Value("${hostname}")
    private String hostName;
    private Integer portNumber = 15672;
    public final static String QUEUE_NAME = "sb-queue";
    public final static String TOPICEXCHANGE_NAME = "sb-exchange";

    @Bean
    public Queue messagingQueue(){
        return new Queue(this.QUEUE_NAME);
    }
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(this.TOPICEXCHANGE_NAME);
    }
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(Consumer receiver){
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }
    //********** MAIN **********//
    public static void main(String[] args) {
        //Initialize Spring and scan for Beans
        SpringApplication.run(Application.class, args).close();

//        Scanner scanner = new Scanner(System.in);
//        String request = null;
//        while (request!="q"){
//            System.out.println("Please insert your request to Chat GPT 3,5");
//            System.out.println("If you don't want to ask more questions, please, insert 'q'");
//            request = scanner.nextLine();

//        }

    }
}