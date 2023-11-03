package co.edu.escuelaing.twitter.handler.tweetComment;

import co.edu.escuelaing.twitter.pojo.Tweet;
import co.edu.escuelaing.twitter.pojo.TweetComment;
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

public class CreateTweetCommentHandler implements RequestHandler<Map<String, Object>, ResponseApiGateway> {
    @Override
    public ResponseApiGateway handleRequest(Map<String, Object> input, Context context) {

        try {
            JsonNode body = new ObjectMapper().readTree((String) input.get("body"));
            TweetComment tweet = new TweetComment();
            tweet.setIdTweet(body.get("idTweet").asText());
            tweet.setUserName(body.get("userName").asText());
            tweet.setComment(body.get("comment").asText());
            tweet.setDate(Timestamp.valueOf(body.get("date").asText()));
            tweet.save(tweet);
            return new ResponseApiGateway(200, tweet, Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));


        } catch (Exception ex) {

            Response response = new Response("Error publishing comment", input);
            try {
                return new ResponseApiGateway(500, response, Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
