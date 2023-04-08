package org.example.model;


import org.springframework.context.ApplicationEvent;

//Class to handle events
public class CustomEvent extends ApplicationEvent {

    private String id;
    /**
     * This it the constructor of CustomEvent class, which will be instantiated to trigger Runner and Consumer sending information.     * @param rabbitTemplate It will be used to send information through RabbitMQ messaging broker.
     * @param id It will have a code that will trigger @EventListener within SpringBoot.
     * @param source Property that is inherited from super.
     */
    public CustomEvent(Object source, String id) {
        super(source);
        this.id = id;
    }

    /**
     * Getter for id.
     * @return The id of the event.
     *
     */
    public String getId() {
        return id;
    }
}
