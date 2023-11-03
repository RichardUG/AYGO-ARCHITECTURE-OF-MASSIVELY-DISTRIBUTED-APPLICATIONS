package co.edu.escuelaing.twitter.handler.tweet;

import co.edu.escuelaing.twitter.pojo.Tweet;
import co.edu.escuelaing.twitter.response.Response;
import co.edu.escuelaing.twitter.response.ResponseApiGateway;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Map;

public class DeleteTweetHandler implements RequestHandler<Map<String, Object>, ResponseApiGateway> {
    @Override
    public ResponseApiGateway handleRequest(Map<String, Object> input, Context context) {

        try {
            Map<String, String> pathParameters = (Map<String, String>) input.get("pathParameters");
            String idTweet = pathParameters.get("idTweet");
            Boolean success = new Tweet().delete(idTweet);

            if (success) {
                return new ResponseApiGateway(204, Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));

            } else {
                return new ResponseApiGateway(404, "Tweet with id: '" + idTweet + "' not found.", Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));
            }
        } catch (Exception ex) {

            Response response = new Response("Error removing tweet", input);
            try {
                return new ResponseApiGateway(500, response, Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
