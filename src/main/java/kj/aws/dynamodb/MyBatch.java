package kj.aws.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public interface MyBatch {
	public void execute(AmazonDynamoDBClient client, String[] args);
}
