package kj.aws.dynamodb;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.DeleteTableResult;

public class DeleteTables implements MyBatch{
	@SuppressWarnings("static-access")
	public void execute(AmazonDynamoDBClient client, String[] args) {
		Options opts = new Options();
		Option tableName = OptionBuilder.withDescription("削除対象テーブル名")
				.hasArg().isRequired().create("tableName");
		opts.addOption(tableName);
		CommandLineParser parser = new BasicParser();
		CommandLine commandLine;
		try {
			commandLine = parser.parse(opts, args);
		} catch(Exception e){
			help(opts);
			return;
		}
		String tablename = "";
		if(commandLine.hasOption("tableName")){
			tablename = commandLine.getOptionValue("tableName");
		} else {
			System.out.println("mmm");
		}
		try {
			DeleteTableResult result = client.deleteTable(tablename);
			System.out.println(result.toString());
		} catch(AmazonClientException e){
			System.out.println(e.getMessage());
		}
	}

	private void help(Options opts) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(App.class.getClass().getName(), opts);			
	}
}
