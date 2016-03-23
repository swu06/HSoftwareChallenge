import static org.junit.Assert.*;

import java.io.StringReader;
import java.io.StringWriter;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;

import org.junit.Test;

public class JsonCleanerTest {

	@Test
	public void testBasicConstructor() {
		try{ 
			JsonParser parser = Json.createParser(new StringReader("{}"));
			JsonGenerator generator = Json.createGenerator(new StringWriter());
			JsonCleaner thisCleaner = new JsonCleaner(parser, generator);
			
			assertNotNull(thisCleaner);
			
		} catch(Exception e) {
			fail("No exceptions should occur during construction");
		}
		
	}

}
