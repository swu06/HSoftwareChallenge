import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonWriter;
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
	JsonStructure jsonst;
	
	public JsonCleaner(Reader reader) {
		JsonReader jReader = Json.createReader(reader);
		jsonst = jReader.read();
	}
	
	public void cleanDuplicates() {
		
	}
	
	public void write(Writer writer) {
		JsonWriter jsonWriter = Json.createWriter(writer);
		jsonWriter.write(jsonst);
	}

	public static void main(String[] args) {
		// Using the default location of "here" if no file is specified.
		File phil = new File("");
		String inputLocation = phil.getAbsolutePath() + "/data.txt";
		String outputLocation = phil.getAbsolutePath() + "/data.txt.out";
		
		System.out.println("Attempting to read file at " + inputLocation);
		
		try(FileReader reader = new FileReader(inputLocation);
				FileWriter writer = new FileWriter(outputLocation);) {
			
			JsonCleaner cleaner = new JsonCleaner(reader);
			cleaner.cleanDuplicates();
			cleaner.write(writer);
			
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
