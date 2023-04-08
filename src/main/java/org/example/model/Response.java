package org.example.model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Response {
    /**
     * Decodes a ResponseEntity from HTTP query and turns it into JsonNode.
     * @return A JsonNode that can be accessed through indexation.
     */
    public JsonNode decodeResponse(ResponseEntity<Object> response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Object responseBody = response.getBody();
        String jsonString = objectMapper.writeValueAsString(responseBody);
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        return jsonNode;
    }
}
