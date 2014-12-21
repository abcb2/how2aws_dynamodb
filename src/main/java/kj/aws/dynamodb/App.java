package kj.aws.dynamodb;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

/*
 * mvn exec:java -Dexec.mainClass=kj.aws.dynamodb.App
 * もしくは mvn clean packageしてから
 * java -jar ./target/how2aws_dynamodb-1.0.0-jar-with-dependencies.jar
 * で実行する
 */
public class App{
	public static void main( String[] args ){
		AmazonDynamoDBClient client = new AmazonDynamoDBClient();
		client.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));
		ListTablesRequest request = new ListTablesRequest();
		ListTablesResult result = client.listTables(request);
		for(String name : result.getTableNames()){
			System.out.println(name);
		}
	}
}
