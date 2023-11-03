package co.edu.escuelaing.twitter.handler.user;

import co.edu.escuelaing.twitter.pojo.User;
import co.edu.escuelaing.twitter.response.Response;
import co.edu.escuelaing.twitter.response.ResponseApiGateway;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ListUserHandler implements RequestHandler<Map<String, Object>, ResponseApiGateway> {
    @Override
    public ResponseApiGateway handleRequest(Map<String, Object> input, Context context) {
        try {
            List<User> users = new User().list();
            return new ResponseApiGateway(200, users, Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));
        } catch (Exception ex) {
            Response response = new Response("Error getting users", input);
            try {
                return new ResponseApiGateway(500, response, Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
