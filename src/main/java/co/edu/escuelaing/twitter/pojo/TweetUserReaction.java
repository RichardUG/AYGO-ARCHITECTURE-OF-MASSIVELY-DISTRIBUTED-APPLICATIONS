package co.edu.escuelaing.twitter.pojo;

import co.edu.escuelaing.twitter.adapter.DynamoDBAdapter;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@DynamoDBTable(tableName = "TweetUserReaction")
public class TweetUserReaction {
    private static final String PRODUCTS_TABLE_NAME = "TweetUserReaction";
    private final AmazonDynamoDB client;
    private static DynamoDBAdapter db_adapter;
    private DynamoDBMapper mapper;
    private String idTweet;
    private String userName;
    private String reaction;
    private Timestamp date;

    public TweetUserReaction() {
        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(PRODUCTS_TABLE_NAME))
                .build();
        db_adapter = DynamoDBAdapter.getInstance();
        this.client = db_adapter.getClient();
        this.mapper = db_adapter.createDbMapper(mapperConfig);
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

    @DynamoDBHashKey(attributeName = "reaction")

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    @DynamoDBHashKey(attributeName = "date")

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public List<TweetUserReaction> list() {
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
        return this.mapper.scan(TweetUserReaction.class, scanExp);
    }

    public TweetUserReaction get(String id, String userName) {
        TweetUserReaction tweetUserReaction = null;

        HashMap<String, AttributeValue> av = new HashMap<String, AttributeValue>();
        av.put(":v1", new AttributeValue().withS(id));
        av.put(":v2", new AttributeValue().withS(userName));

        DynamoDBQueryExpression<TweetUserReaction> queryExp = new DynamoDBQueryExpression<TweetUserReaction>()
                .withKeyConditionExpression("idTweet = :v1")
                .withKeyConditionExpression("userName = :v2")
                .withExpressionAttributeValues(av);

        PaginatedQueryList<TweetUserReaction> result = this.mapper.query(TweetUserReaction.class, queryExp);
        if (result.size() > 0) {
            tweetUserReaction = result.get(0);
        }
        return tweetUserReaction;
    }

    public void save(TweetUserReaction tweetUserReaction) throws IOException {
        this.mapper.save(tweetUserReaction);
    }


    public Boolean delete(String id, String userName) {
        TweetUserReaction tweetUserReaction = null;
        tweetUserReaction = get(id, userName);
        if (tweetUserReaction != null) {
            this.mapper.delete(tweetUserReaction);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TweetUserReaction{" +
                "idTweet=" + idTweet +
                ", userName='" + userName + '\'' +
                ", reaction=" + reaction +
                '}';
    }
}
