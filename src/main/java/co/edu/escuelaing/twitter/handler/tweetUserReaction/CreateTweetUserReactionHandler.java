package co.edu.escuelaing.twitter.handler.tweetUserReaction;

import co.edu.escuelaing.twitter.pojo.TweetUserReaction;
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

public class CreateTweetUserReactionHandler implements RequestHandler<Map<String, Object>, ResponseApiGateway> {
    @Override
    public ResponseApiGateway handleRequest(Map<String, Object> input, Context context) {

        try {
            JsonNode body = new ObjectMapper().readTree((String) input.get("body"));
            TweetUserReaction reaction = new TweetUserReaction();
            reaction.setIdTweet(body.get("idTweet").asText());
            reaction.setUserName(body.get("userName").asText());
            reaction.setReaction(body.get("reaction").asText());
            reaction.setDate(Timestamp.valueOf(body.get("date").asText()));
            reaction.save(reaction);
            return new ResponseApiGateway(200, reaction, Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));


        } catch (Exception ex) {

            Response response = new Response("Error reacting", input);
            try {
                return new ResponseApiGateway(500, response, Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
