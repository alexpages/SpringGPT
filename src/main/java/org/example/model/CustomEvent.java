package org.example.model;


import org.springframework.context.ApplicationEvent;

//Class to handle events
public class CustomEvent extends ApplicationEvent {

    private String id;

    public CustomEvent(Object source, String id) {
        super(source);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
