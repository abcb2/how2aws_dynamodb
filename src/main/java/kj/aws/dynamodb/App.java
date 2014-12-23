package kj.aws.dynamodb;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

/*
 * mvn exec:java -Dexec.mainClass=kj.aws.dynamodb.App
 * もしくは mvn clean packageしてから
 * java -jar ./target/how2aws_dynamodb-1.0.0-jar-with-dependencies.jar
 * で実行する
 */
public class App{
	public final static AmazonDynamoDBClient client = new AmazonDynamoDBClient();
	static {
		client.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));
	}
	@SuppressWarnings("static-access")
	public static void main( String[] args ){
		Options opts = new Options();
		Option mode = OptionBuilder.withDescription("動作モードを指定").withArgName("mode")
				.withLongOpt("m").hasArg().isRequired().create("mode");
		Option others = OptionBuilder.withDescription("個別のオプション").withArgName("others")
				.withLongOpt("o").hasArg().create("others");
		Option help = OptionBuilder.withDescription("ヘルプを表示").withArgName("help")
				.withLongOpt("h").create("help");
		opts.addOption(mode);
		opts.addOption(others);
		opts.addOption(help);
		
		CommandLineParser parser = new BasicParser();
		CommandLine commandLine;
		try {
			commandLine = parser.parse(opts, args);
		} catch (Exception e){
			help(opts);
			return;
		}
		
		String execMode = "";
		String[] otherOpts = {};
		if(commandLine.hasOption("help")){
			help(opts);
			return;
		}
		if(commandLine.hasOption("mode")){
			execMode = commandLine.getOptionValue("mode");
		}
		if(commandLine.hasOption("others")){
			otherOpts = commandLine.getOptionValue("others").split(" ", 0);
		}
		
		if(execMode.equals("list-tables")){
			ListTables klass = new ListTables();
			klass.execute(client, otherOpts);
		}else if(execMode.equals("delete-table")){
			DeleteTables klass = new DeleteTables();
			klass.execute(client, otherOpts);
		}
		else {
			System.out.println("mmm.. " + execMode);
		}
		
	}
	private static void help(Options opts) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(App.class.getClass().getName(), opts);		
	}
}
