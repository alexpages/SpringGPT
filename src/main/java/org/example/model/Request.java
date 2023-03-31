package org.example.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class Request {
    private String model = "gpt-3.5-turbo";
    private String prompt; //Message
    private Double temperature = 0.5;
    private int max_tokens = 7;
    private int top_p = 1;
    private int frequency_penalty = 1;
    private int presence_penalty = 0;

    //Constructor specifying model
    @Autowired
    public Request(String model, String prompt) {
        this.model = model;
        this.prompt = prompt;
    }
    //Constructor with given model
    @Autowired
    public Request(String prompt) {
        this.prompt = prompt;
    }

    public String createBody(){
        //creation of json
        String requestBody = "{\"prompt\": \"" + this.prompt +
                "\", \"max_tokens\": " + this.max_tokens +
                "\", \"temperature\": \"" + this.temperature +
                "\", \"top_p\": \""+ this.top_p +
                "\", \"frequency_penalty\": \"" + this.frequency_penalty +
                "\", \"presence_penalty\": \"" + this.presence_penalty + "}";
        return requestBody;
    }

    //********** GETTERS/SETTERS **********//
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getPrompt() {
        return prompt;
    }
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
    public Double getTemperature() {
        return temperature;
    }
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
    public int getMax_tokens() {
        return max_tokens;
    }
    public void setMax_tokens(int max_tokens) {
        this.max_tokens = max_tokens;
    }
    public int getTop_p() {
        return top_p;
    }
    public void setTop_p(int top_p) {
        this.top_p = top_p;
    }
    public int getFrequency_penalty() {
        return frequency_penalty;
    }
    public void setFrequency_penalty(int frequency_penalty) {
        this.frequency_penalty = frequency_penalty;
    }
    public int getPresence_penalty() {
        return presence_penalty;
    }
    public void setPresence_penalty(int presence_penalty) {
        this.presence_penalty = presence_penalty;
    }


}
