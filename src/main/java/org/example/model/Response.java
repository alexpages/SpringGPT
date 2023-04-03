package org.example.model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Response {

    public JsonNode decodeResponse(ResponseEntity<Object> response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Object responseBody = response.getBody();
        String jsonString = objectMapper.writeValueAsString(responseBody);
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        return jsonNode;
    }
}
