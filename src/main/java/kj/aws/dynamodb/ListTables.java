package kj.aws.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

public class ListTables implements MyBatch{
	public void execute(AmazonDynamoDBClient client, String[] args) {
		ListTablesResult result = client.listTables();
		for(String name : result.getTableNames()){
			System.out.println(name);
		}
	}
}
