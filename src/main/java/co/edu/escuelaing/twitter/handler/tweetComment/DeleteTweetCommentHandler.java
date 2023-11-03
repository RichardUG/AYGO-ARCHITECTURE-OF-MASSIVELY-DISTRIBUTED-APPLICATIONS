package co.edu.escuelaing.twitter.handler.tweetComment;

import co.edu.escuelaing.twitter.pojo.Tweet;
import co.edu.escuelaing.twitter.pojo.TweetComment;
import co.edu.escuelaing.twitter.response.Response;
import co.edu.escuelaing.twitter.response.ResponseApiGateway;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Collections;
import java.util.Map;

public class DeleteTweetCommentHandler implements RequestHandler<Map<String, Object>, ResponseApiGateway> {
    @Override
    public ResponseApiGateway handleRequest(Map<String, Object> input, Context context) {

        try {
            Map<String, String> pathParameters = (Map<String, String>) input.get("pathParameters");
            String idComment = pathParameters.get("idComment");
            Boolean success = new TweetComment().delete(idComment);

            if (success) {
                return new ResponseApiGateway(204, Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));

            } else {
                return new ResponseApiGateway(404, "Comment with id: '" + idComment + "' not found.", Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));
            }
        } catch (Exception ex) {

            Response response = new Response("Error removing comment", input);
            try {
                return new ResponseApiGateway(500, response, Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
