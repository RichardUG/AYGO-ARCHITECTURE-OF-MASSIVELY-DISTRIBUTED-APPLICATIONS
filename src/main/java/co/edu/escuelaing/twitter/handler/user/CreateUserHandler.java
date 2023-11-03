package co.edu.escuelaing.twitter.handler.user;

import co.edu.escuelaing.twitter.pojo.User;
import co.edu.escuelaing.twitter.response.Response;
import co.edu.escuelaing.twitter.response.ResponseApiGateway;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.Map;

public class CreateUserHandler implements RequestHandler<Map<String, Object>, ResponseApiGateway> {
    @Override
    public ResponseApiGateway handleRequest(Map<String, Object> input, Context context) {

        try {
            JsonNode body = new ObjectMapper().readTree((String) input.get("body"));
            User user = new User();
            user.setUserName(body.get("userName").asText());
            user.setEmail(body.get("email").asText());
            user.setPassword(body.get("password").asText());
            user.setNombre(body.get("nombre").asText());
            user.save(user);
            return new ResponseApiGateway(200, user, Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));


        } catch (Exception ex) {

            Response response = new Response("Error creating user", input);
            try {
                return new ResponseApiGateway(500, response, Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
