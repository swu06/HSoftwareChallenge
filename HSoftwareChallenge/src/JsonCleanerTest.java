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
			JsonCleaner thisCleaner = new JsonCleaner(new StringReader("{}"));
			
			assertNotNull("A valid object should be created", thisCleaner);
			
		} catch(Exception e) {
			fail("No exceptions should occur during construction");
		}
		
	}
	
	@Test
	public void testDirectCopyEmpty() {
		try{ 
			StringWriter sw = new StringWriter();
			JsonCleaner thisCleaner = new JsonCleaner(new StringReader("{}"));
			thisCleaner.write(sw);
			System.out.println("Generator String:" + sw.toString());
			assertEquals("Cleaner should pass back basic copied document", 
					"{}", sw.toString());
		} catch(Exception e) {
			e.printStackTrace();
			fail("No exceptions should occur during construction");
		}		
	}
	
	@Test
	public void testDirectCopyOneKeyPair() {
		try{ 
			StringWriter sw = new StringWriter();
			JsonCleaner thisCleaner = new JsonCleaner(new StringReader("{\"sarah\":\"hello\"}"));
			thisCleaner.write(sw);
			System.out.println("Generator String:" + sw.toString());
			assertEquals("Cleaner should pass back basic copied document", 
					"{\"sarah\":\"hello\"}", sw.toString());
		} catch(Exception e) {
			e.printStackTrace();
			fail("No exceptions should occur during construction");
		}		
	}

}
