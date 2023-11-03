package co.edu.escuelaing.twitter.handler.tweet;

import co.edu.escuelaing.twitter.response.Response;
import co.edu.escuelaing.twitter.response.ResponseApiGateway;
import co.edu.escuelaing.twitter.pojo.Tweet;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ListTweetHandler implements RequestHandler<Map<String, Object>, ResponseApiGateway> {
    @Override
    public ResponseApiGateway handleRequest(Map<String, Object> input, Context context) {
        try {
            List<Tweet> tweets = new Tweet().list();
            return new ResponseApiGateway(200, tweets, Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));
        } catch (Exception ex) {
            Response response = new Response("Error getting tweets", input);
            try {
                return new ResponseApiGateway(500, response, Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
