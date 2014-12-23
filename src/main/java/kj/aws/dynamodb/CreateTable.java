package kj.aws.dynamodb;

import java.util.ArrayList;
import org.apache.commons.cli.Options;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

public class CreateTable implements MyBatch {
	public void execute(AmazonDynamoDBClient client, String[] args) {
		System.out.println("create table start");
		
		ArrayList<KeySchemaElement> ks = new ArrayList<KeySchemaElement>();
		ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
		ks.add(
				new KeySchemaElement().withAttributeName("Id").withKeyType(KeyType.HASH)
		);
		attributeDefinitions.add(
				new AttributeDefinition().withAttributeName("Id").withAttributeType("N")
		);
		ProvisionedThroughput pt = new ProvisionedThroughput()
			.withReadCapacityUnits(10L)
			.withWriteCapacityUnits(5L);
		
		CreateTableRequest request = new CreateTableRequest()
			.withTableName("productCatalog")
			.withKeySchema(ks)
			.withProvisionedThroughput(pt);
		request.setAttributeDefinitions(attributeDefinitions);
		
		try {
			CreateTableResult result = client.createTable(request);
		} catch (AmazonServiceException ase){
			System.err.println(ase.getMessage());
		}
		System.out.println("create table finish");
	}
}
