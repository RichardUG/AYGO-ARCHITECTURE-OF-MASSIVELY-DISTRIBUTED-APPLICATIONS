package co.edu.escuelaing.twitter.adapter;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

public class DynamoDBAdapter {

    private final AmazonDynamoDB client;
    private static DynamoDBAdapter db_adapter;
    private DynamoDBMapper mapper;

    private DynamoDBAdapter() {
        this.client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .build();
    }
    public static DynamoDBAdapter getInstance() {
        if (db_adapter == null)
            db_adapter = new DynamoDBAdapter();

        return db_adapter;
    }
    public DynamoDBMapper createDbMapper(DynamoDBMapperConfig mapperConfig) {
        if (this.client != null)
            mapper = new DynamoDBMapper(this.client, mapperConfig);

        return this.mapper;
    }

    public AmazonDynamoDB getClient() {
        return client;
    }

    public static DynamoDBAdapter getDb_adapter() {
        return db_adapter;
    }

    public static void setDb_adapter(DynamoDBAdapter db_adapter) {
        DynamoDBAdapter.db_adapter = db_adapter;
    }

    public DynamoDBMapper getMapper() {
        return mapper;
    }

    public void setMapper(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }
}
