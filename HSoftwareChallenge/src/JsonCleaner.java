import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

/*
 * This class removes redundancy in a JSON file by removing items within children elements that have already
 * appeared in the parent. Duplicate items are detected by comparing the name of the object.  This class writes
 * out the resulting file at the same time the input is read and a main method is provided to allow
 * default access.
 */
public class JsonCleaner {
	JsonParser parser;
	JsonGenerator generator;
	
	public JsonCleaner(JsonParser parser, JsonGenerator generator) {
		this.parser = parser;
		this.generator = generator;
	}
	
	public void cleanDocumentAndOutput() {
		while(parser.hasNext()) {
			Event nextEvent = parser.next();
			if(nextEvent.equals(Event.KEY_NAME) || nextEvent.equals(Event.VALUE_STRING) || nextEvent.equals(Event.VALUE_NUMBER)) {
				System.out.println(parser.getString());
			}
			
		}
	}

	public static void main(String[] args) {
		// Using the default location of "here" if no file is specified.
		File phil = new File("");
		String inputLocation = phil.getAbsolutePath() + "/data.txt";
		String outputLocation = phil.getAbsolutePath() + "/data.txt.out";
		
		System.out.println("Attempting to read file at " + inputLocation);
		
		try(FileReader reader = new FileReader(inputLocation);
				JsonParser parser = Json.createParser(reader);
				FileWriter writer = new FileWriter(outputLocation);
				JsonGenerator generator = Json.createGenerator(writer)) {
			
			JsonCleaner cleaner = new JsonCleaner(parser, generator);
			cleaner.cleanDocumentAndOutput();
		} catch (FileNotFoundException e) {
			System.out.println("The file could not be found.  Please check "
					+ "the location and run again."
					+ "\nNote that some systems are case sensitive so "
					+ "\"data.txt\" is different than \"DATA.txt\".");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
