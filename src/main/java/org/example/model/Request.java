package org.example.model;

import org.springframework.stereotype.Component;

@Component
public class Request {
    private String model;
    private String prompt; //Message
    private Double temperature = 0.5;
    private int max_tokens = 7;
    private int top_p = 1;
    private int frequency_penalty = 1;
    private int presence_penalty = 0;

    public Request(String model, String prompt, Double temperature, int max_tokens, int top_p, int frequency_penalty, int presence_penalty) {
        this.model = model;
        this.prompt = prompt;
        this.temperature = temperature;
        this.max_tokens = max_tokens;
        this.top_p = top_p;
        this.frequency_penalty = frequency_penalty;
        this.presence_penalty = presence_penalty;
    }
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
