import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.JsonWriter;

/*
 * This class removes redundancy in a JSON file by removing items within 
 * children elements that have already appeared in the parent. Duplicate items
 * are detected by comparing the name of the object.  This class writes out the
 * cleaned Json file to the writer provided.
 * 
 * @author Sarah Wu
 */
public class JsonCleaner {
	JsonStructure oldFile;
	JsonStructure newFile;

	/*
	 * Create our cleaner object.
	 * 
	 * @param reader a valid reader object that contains the Json File data
	 */
	public JsonCleaner(Reader reader) {
		JsonReader jReader = Json.createReader(reader);
		oldFile = jReader.read();
		newFile = oldFile;
	}

	/*
	 * Cleans the Json Structure that was read in by the constructor
	 */
	public void cleanDuplicates() {
		Set<String> emptyKeys = new HashSet<String>();
		newFile = (JsonStructure) cleanDuplicatesRecursively(oldFile, emptyKeys);
	}

	/*
	 * Actual method that cleans the JsonFile. It is called recursively over the
	 * whole tree of the Json file
	 * 
	 * @param: currentTree the JsonValue that represents the current section of
	 * the JsonFile
	 * 
	 * @param: previousKeys the set of keys that were found within the parent
	 * sections of the Json File
	 */
	private JsonValue cleanDuplicatesRecursively(JsonValue currentTree, 
												Set<String> previousKeys) {
		// All values are passed directly through except the two cases below
		JsonValue toReturn = currentTree;
		
		if (currentTree.getValueType() == JsonValue.ValueType.OBJECT) {
			// JsonObjects contain multiple items and require iterating
			JsonObject currentObject = (JsonObject) currentTree;

			// Gather objects from this level and add them to our list of keys
			Set<String> currentKeys = currentObject.keySet();
			Set<String> combinedKeys = new HashSet<String>(currentKeys);
			combinedKeys.addAll(previousKeys);

			// Iterate over all child values
			JsonObjectBuilder currentModel = Json.createObjectBuilder();
			for (String key : currentKeys) {
				// Check to make sure this key hasn't been seen before.
				if (!previousKeys.contains(key)) {
					currentModel.add(key, 
							cleanDuplicatesRecursively(currentObject.get(key), combinedKeys));
				}
			}

			toReturn = currentModel.build();

		} else if (currentTree.getValueType() == JsonValue.ValueType.ARRAY) {
			// JsonArrays also contain multiple objects and require iterating
			JsonArray currentArray = (JsonArray) currentTree;

			// (No additional keys to grab at this level)

			// Iterate over all child values
			JsonArrayBuilder currentModel = Json.createArrayBuilder();
			for (JsonValue currentValue : currentArray) {
				currentModel.add(cleanDuplicatesRecursively(currentValue, previousKeys));
			}

			toReturn = currentModel.build();
	
		}
		
		return toReturn;

	}

	/*
	 * This method writes the "current" JsonStructure to the writer provided.
	 * Note that the cleanDuplicates method should be called first if the user
	 * wants to write out the structure without duplicate values. This method
	 * can be called repeatedly to write out multiple copies of the Json file
	 * that this class represents.
	 * 
	 * @param writer an object that can write out to the correct location
	 */
	public void write(Writer writer) {
		JsonWriter jsonWriter = Json.createWriter(writer);
		jsonWriter.write(newFile);
	}

	/*
	 * This main method is provided so this class can be called by batch
	 * processes or other languages that require using the system java command
	 * to pass in file locations for cleaning.
	 * 
	 * @param args If provided, the first element of this array must be the
	 * absolute path of the json file.  If not provided, the code will look
	 * in the "current location" for data.txt.  The output is written to the
	 * same location as the input, with ".out" appended.
	 */
	public static void main(String[] args) {
		// Using the default location of "here" if no file is specified.
		File phil = new File("");
		String inputLocation = phil.getAbsolutePath() + "/data.txt";
		String outputLocation = phil.getAbsolutePath() + "/data.txt.out";

		// Over-write with the first input parameter, if provided.
		if (args.length > 0) {
			inputLocation = args[0];
			outputLocation = args[0] + ".out";
		}

		System.out.println("Attempting to read file at " + inputLocation);

		// Load data, remove duplicates, and write results
		try (FileReader reader = new FileReader(inputLocation); 
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
