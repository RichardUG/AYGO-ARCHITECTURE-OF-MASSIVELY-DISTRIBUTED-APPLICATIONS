package co.edu.escuelaing.twitter.pojo;

import co.edu.escuelaing.twitter.adapter.DynamoDBAdapter;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@DynamoDBTable(tableName = "TweetComment")
public class TweetComment {

    private static final String PRODUCTS_TABLE_NAME = "TweetComment";
    private final AmazonDynamoDB client;
    private static DynamoDBAdapter db_adapter;
    private DynamoDBMapper mapper;
    private String idComment;
    private String idTweet;
    private String userName;
    private String comment;
    private Timestamp date;

    public TweetComment() {
        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(PRODUCTS_TABLE_NAME))
                .build();
        db_adapter = DynamoDBAdapter.getInstance();
        this.client = db_adapter.getClient();
        this.mapper = db_adapter.createDbMapper(mapperConfig);
    }

    @DynamoDBHashKey(attributeName = "idComment")
    @DynamoDBAutoGeneratedKey
    public String getIdComment() {
        return idComment;
    }

    public void setIdComment(String idComment) {
        this.idComment = idComment;
    }

    @DynamoDBHashKey(attributeName = "idTweet")

    public String getIdTweet() {
        return idTweet;
    }

    public void setIdTweet(String idTweet) {
        this.idTweet = idTweet;
    }

    @DynamoDBHashKey(attributeName = "userName")

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @DynamoDBHashKey(attributeName = "comment")

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @DynamoDBHashKey(attributeName = "date")

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public List<TweetComment> list() {
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
        return this.mapper.scan(TweetComment.class, scanExp);
    }

    public TweetComment get(String id) {
        TweetComment tweetComment = null;

        HashMap<String, AttributeValue> av = new HashMap<String, AttributeValue>();
        av.put(":v1", new AttributeValue().withS(id));

        DynamoDBQueryExpression<TweetComment> queryExp = new DynamoDBQueryExpression<TweetComment>()
                .withKeyConditionExpression("idComment = :v1")
                .withExpressionAttributeValues(av);

        PaginatedQueryList<TweetComment> result = this.mapper.query(TweetComment.class, queryExp);
        if (result.size() > 0) {
            tweetComment = result.get(0);
        }
        return tweetComment;
    }

    public void save(TweetComment tweetComment) throws IOException {
        this.mapper.save(tweetComment);
    }


    public Boolean delete(String id) {
        TweetComment tweetComment = null;
        tweetComment = get(id);
        if (tweetComment != null) {
            this.mapper.delete(tweetComment);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TweetComment{" +
                "idComment=" + idComment +
                ", idTweet=" + idTweet +
                ", userName='" + userName + '\'' +
                ", date=" + date +
                '}';
    }
}
