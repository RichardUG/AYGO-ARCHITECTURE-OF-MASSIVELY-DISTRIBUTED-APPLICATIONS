package co.edu.escuelaing.twitter.handler.tweetUserReaction;

import co.edu.escuelaing.twitter.pojo.TweetUserReaction;
import co.edu.escuelaing.twitter.response.Response;
import co.edu.escuelaing.twitter.response.ResponseApiGateway;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Collections;
import java.util.Map;

public class GetTweetUserReactionHandler implements RequestHandler<Map<String, Object>, ResponseApiGateway> {
    @Override
    public ResponseApiGateway handleRequest(Map<String, Object> input, Context context) {
        try {
            Map<String, String> pathParameters = (Map<String, String>) input.get("pathParameters");
            String idTweet = pathParameters.get("idTweet");
            String userName = pathParameters.get("userName");
            TweetUserReaction reaction = new TweetUserReaction().get(idTweet, userName);
            if (reaction != null) {
                return new ResponseApiGateway(200, reaction, Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));
            }
            return new ResponseApiGateway(404, "Reaction with for tweet with id: '" + idTweet + "' from '" + userName + "' not found.", Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));
        } catch (Exception ex) {
            Response response = new Response("Error getting tweet", input);

            try {
                return new ResponseApiGateway(500, response, Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
