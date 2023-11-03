package co.edu.escuelaing.twitter.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ResponseApiGateway {
    private ObjectMapper objectMapper = new ObjectMapper();
    private int statusCode;
    private String body;
    private final Map<String, String> headers;

    public ResponseApiGateway(int statusCode, Object body, Map<String, String> headers) throws JsonProcessingException {
        this.statusCode = statusCode;
        this.body = objectMapper.writeValueAsString(body);
        this.headers = headers;
    }

    public ResponseApiGateway(int statusCode, Map<String, String> headers) {
        this.statusCode = statusCode;
        this.headers = headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

}
