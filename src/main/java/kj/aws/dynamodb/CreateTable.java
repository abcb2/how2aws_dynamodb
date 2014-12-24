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
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

public class CreateTable implements MyBatch {
	public void execute(AmazonDynamoDBClient client, String[] args) {
		System.out.println("create table start");
		createProductCatalogTable(client);
		createFrorumsTable(client);
		createThreadTable(client);
		createReplyTable(client);
		System.out.println("create table finish");
	}
	private void createReplyTable(AmazonDynamoDBClient client) {
		System.out.println("\tcreate Reply table");
		ArrayList<KeySchemaElement> ks = new ArrayList<KeySchemaElement>();
		ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
		
		ks.add(new KeySchemaElement().withAttributeName("Id").withKeyType(KeyType.HASH));
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("Id").withAttributeType("S"));
		
		ks.add(new KeySchemaElement().withAttributeName("ReplyDateTime").withKeyType(KeyType.RANGE));
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("ReplyDateTime").withAttributeType("S"));
		
		ProvisionedThroughput pt = new ProvisionedThroughput()
		.withReadCapacityUnits(1L)
		.withWriteCapacityUnits(1L);
		
		// local secondary index
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("PostedBy").withAttributeType("S"));
		ArrayList<KeySchemaElement> iks = new ArrayList<KeySchemaElement>();
		iks.add(new KeySchemaElement().withAttributeName("Id").withKeyType(KeyType.HASH));
		iks.add(new KeySchemaElement().withAttributeName("PostedBy").withKeyType(KeyType.RANGE));
		
		LocalSecondaryIndex lsi = new LocalSecondaryIndex();
		lsi.withIndexName("PostedByIndex").withKeySchema(iks).withProjection(new Projection().withProjectionType(ProjectionType.KEYS_ONLY));
		
		ArrayList<LocalSecondaryIndex> localSecondaryIndexes = new ArrayList<LocalSecondaryIndex>();
		localSecondaryIndexes.add(lsi);
		
		CreateTableRequest request = new CreateTableRequest()
		.withTableName("Reply")
		.withKeySchema(ks)
		.withProvisionedThroughput(pt);

		request.setAttributeDefinitions(attributeDefinitions);
		request.setLocalSecondaryIndexes(localSecondaryIndexes);
		
		try {
			CreateTableResult result = client.createTable(request);
		} catch(AmazonServiceException ase){
			System.err.println(ase.getMessage());
		}
	}
	private void createThreadTable(AmazonDynamoDBClient client) {
		System.out.println("\tcreate Thread table");
		ArrayList<KeySchemaElement> ks = new ArrayList<KeySchemaElement>();
		ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
		
		ks.add(new KeySchemaElement().withAttributeName("ForumName").withKeyType(KeyType.HASH));
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("ForumName").withAttributeType("S"));
		
		ks.add(new KeySchemaElement().withAttributeName("Subject").withKeyType(KeyType.RANGE));
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("Subject").withAttributeType("S"));
		
		ProvisionedThroughput pt = new ProvisionedThroughput()
		.withReadCapacityUnits(1L)
		.withWriteCapacityUnits(1L);
		
		CreateTableRequest request = new CreateTableRequest()
		.withTableName("Thread")
		.withKeySchema(ks)
		.withProvisionedThroughput(pt);
		request.setAttributeDefinitions(attributeDefinitions);
		
		try {
			CreateTableResult result = client.createTable(request);
		} catch(AmazonServiceException ase){
			System.err.println(ase.getMessage());
		}
	}
	private void createFrorumsTable(AmazonDynamoDBClient client) {
		System.out.println("\tcreate Forum table");
		ArrayList<KeySchemaElement> ks = new ArrayList<KeySchemaElement>();
		ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
		ks.add(new KeySchemaElement().withAttributeName("Name").withKeyType(KeyType.HASH));
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("Name").withAttributeType("S"));
		ProvisionedThroughput pt = new ProvisionedThroughput()
		.withReadCapacityUnits(1L)
		.withWriteCapacityUnits(1L);
		
		CreateTableRequest request = new CreateTableRequest()
		.withTableName("Forum")
		.withKeySchema(ks)
		.withProvisionedThroughput(pt);
		request.setAttributeDefinitions(attributeDefinitions);
		try {
			CreateTableResult result = client.createTable(request);
		} catch(AmazonServiceException ase){
			System.err.println(ase.getMessage());
		}
	}
	private void createProductCatalogTable(AmazonDynamoDBClient client){
		System.out.println("\tcreate productCatalog table");
		ArrayList<KeySchemaElement> ks = new ArrayList<KeySchemaElement>();
		ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
		ks.add(new KeySchemaElement().withAttributeName("Id").withKeyType(KeyType.HASH));
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("Id").withAttributeType("N"));
		ProvisionedThroughput pt = new ProvisionedThroughput()
		.withReadCapacityUnits(1L)
		.withWriteCapacityUnits(1L);

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
	}
}
