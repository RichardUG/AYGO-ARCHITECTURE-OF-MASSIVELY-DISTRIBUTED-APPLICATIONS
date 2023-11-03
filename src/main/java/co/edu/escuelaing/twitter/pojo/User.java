package co.edu.escuelaing.twitter.pojo;

import co.edu.escuelaing.twitter.adapter.DynamoDBAdapter;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@DynamoDBTable(tableName = "User")
public class User {
    private static final String PRODUCTS_TABLE_NAME = "User";
    private final AmazonDynamoDB client;
    private static DynamoDBAdapter db_adapter;
    private DynamoDBMapper mapper;
    private String userName;
    private String email;
    private String password;
    private String nombre;

    public User() {
        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(PRODUCTS_TABLE_NAME))
                .build();
        db_adapter = DynamoDBAdapter.getInstance();
        this.client = db_adapter.getClient();
        this.mapper = db_adapter.createDbMapper(mapperConfig);
    }
    @DynamoDBHashKey(attributeName = "userName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    @DynamoDBHashKey(attributeName = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @DynamoDBHashKey(attributeName = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @DynamoDBHashKey(attributeName = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public List<User> list(){
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
        return this.mapper.scan(User.class, scanExp);
    }

    public User get(String userName) {
        User user = null;

        HashMap<String, AttributeValue> av = new HashMap<String, AttributeValue>();
        av.put(":v1", new AttributeValue().withS(userName));

        DynamoDBQueryExpression<User> queryExp = new DynamoDBQueryExpression<User>()
                .withKeyConditionExpression("userName = :v1")
                .withExpressionAttributeValues(av);

        PaginatedQueryList<User> result = this.mapper.query(User.class, queryExp);
        if (result.size() > 0) {
            user = result.get(0);
        }
        return user;
    }

    public void save(User user) throws IOException {
        this.mapper.save(user);
    }


    public Boolean delete(String userName){
        User user = null;
        user = get(userName);
        if (user != null) {
            this.mapper.delete(user);
        } else {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
