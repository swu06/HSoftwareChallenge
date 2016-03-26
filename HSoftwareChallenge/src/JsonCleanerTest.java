import static org.junit.Assert.*;

import java.io.StringReader;
import java.io.StringWriter;

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
	public void testWriteWithoutDuplicateRemoval() {
		String inputJson = "{"
								+ "\"hello\":\"world\","
								+ "\"goodbye\": {"
									+ "\"hello\":\"moon\","
									+ "\"this\":\"stays\""
								+ "}"
						+ "}";
		String expectedJson = "{"
								+ "\"hello\":\"world\","
								+ "\"goodbye\":{"
									+ "\"hello\":\"moon\","
									+ "\"this\":\"stays\""
								+ "}"
							+ "}";
		try{ 
			StringWriter sw = new StringWriter();
			JsonCleaner thisCleaner = new JsonCleaner(new StringReader(inputJson));
			thisCleaner.write(sw);
			assertEquals("Cleaner does not copy until removeDuplicates is called", 
					expectedJson, sw.toString());
		} catch(Exception e) {
			e.printStackTrace();
			fail("No exceptions should occur during construction");
		}		
	}

	@Test
	public void testCleanNoDups() {
		String inputJson = "{"
								+ "\"hello\":\"world\","
								+ "\"goodbye\": {"
									+ "\"goodnight\":\"moon\","
									+ "\"this\":\"stays\""
								+ "}"
						+ "}";
		String expectedJson = "{"
								+ "\"hello\":\"world\","
								+ "\"goodbye\":{"
								+ "\"goodnight\":\"moon\","
								+ "\"this\":\"stays\""
								+ "}"
							+ "}";
		try{ 
			StringWriter sw = new StringWriter();
			JsonCleaner thisCleaner = new JsonCleaner(new StringReader(inputJson));
			thisCleaner.cleanDuplicates();
			thisCleaner.write(sw);
			assertEquals("Problem during cleaning process: non duplicate items were removed", 
					expectedJson, sw.toString());
		} catch(Exception e) {
			e.printStackTrace();
			fail("No exceptions should occur during construction");
		}		
	}

	
	@Test
	public void testCleanOneDup() {
		String inputJson = "{"
								+ "\"hello\":\"world\","
								+ "\"goodbye\": {"
									+ "\"hello\":\"moon\","
									+ "\"this\":\"stays\""
								+ "}"
						+ "}";
		String expectedJson = "{"
								+ "\"hello\":\"world\","
								+ "\"goodbye\":{"
									+ "\"this\":\"stays\""
								+ "}"
							+ "}";
		try{ 
			StringWriter sw = new StringWriter();
			JsonCleaner thisCleaner = new JsonCleaner(new StringReader(inputJson));
			thisCleaner.cleanDuplicates();
			thisCleaner.write(sw);
			assertEquals("Problem during cleaning process", 
					expectedJson, sw.toString());
		} catch(Exception e) {
			e.printStackTrace();
			fail("No exceptions should occur during construction");
		}		
	}
	
	@Test
	public void testCleanWithArrays() {
		String inputJson = "{"
								+ "\"hello\":\"world\","
								+ "\"goodbye\":[{"
									+ "\"hello\":\"moon\","
									+ "\"this\":\"stays\""
								+ "}]"
						+ "}";
		String expectedJson = "{"
								+ "\"hello\":\"world\","
								+ "\"goodbye\":[{"
									+ "\"this\":\"stays\""
								+ "}]"
							+ "}";
		try{ 
			StringWriter sw = new StringWriter();
			JsonCleaner thisCleaner = new JsonCleaner(new StringReader(inputJson));
			thisCleaner.cleanDuplicates();
			thisCleaner.write(sw);
			assertEquals("Problem during cleaning process", 
					expectedJson, sw.toString());
		} catch(Exception e) {
			e.printStackTrace();
			fail("No exceptions should occur during construction");
		}		
	}
	
	@Test
	public void testCleanStartAsArray() {
		String inputJson = "[{"
								+ "\"hello\":\"world\","
								+ "\"goodbye\":[{"
									+ "\"hello\":\"moon\","
									+ "\"this\":\"stays\""
								+ "}]"
							+ "}]";
		String expectedJson = "[{"
									+ "\"hello\":\"world\","
									+ "\"goodbye\":[{"
										+ "\"this\":\"stays\""
									+ "}]"
								+ "}]";
		try{ 
			StringWriter sw = new StringWriter();
			JsonCleaner thisCleaner = new JsonCleaner(new StringReader(inputJson));
			thisCleaner.cleanDuplicates();
			thisCleaner.write(sw);
			assertEquals("Problem during cleaning process", 
					expectedJson, sw.toString());
		} catch(Exception e) {
			e.printStackTrace();
			fail("No exceptions should occur during construction");
		}		
	}
	
	@Test
	public void testCleanSiblings() {
		String inputJson = "{"
								+ "\"hello\":\"world\","
								+ "\"goodbye\": {"
									+ "\"hello\":\"moon\","
									+ "\"this\":\"stays\""
								+ "},"
								+ "\"sibling\": {"
									+ "\"hello\":\"moon\","
									+ "\"this\":\"stays\""
								+ "}"
						+ "}";
		String expectedJson = "{"
								+ "\"hello\":\"world\","
								+ "\"goodbye\":{"
									+ "\"this\":\"stays\""
								+ "},"
								+ "\"sibling\":{"
									+ "\"this\":\"stays\""
								+ "}"
							+ "}";
		try{ 
			StringWriter sw = new StringWriter();
			JsonCleaner thisCleaner = new JsonCleaner(new StringReader(inputJson));
			thisCleaner.cleanDuplicates();
			thisCleaner.write(sw);
			assertEquals("Problem during cleaning process", 
					expectedJson, sw.toString());
		} catch(Exception e) {
			e.printStackTrace();
			fail("No exceptions should occur during construction");
		}		
	}
	
	@Test
	public void testCleanComplex() {
		String inputJson = "{"
								+ "\"hello\":\"world\","
								+ "\"goodbye\":{"
									+ "\"this\":\"hello\","
									+ "\"that\":[\"moon\","
												+ "{\"hello\":\"this\"},"
												+ "\"hello\""
												+ "]"
								+ "}"
						+ "}";
		String expectedJson = "{"
								+ "\"hello\":\"world\","
								+ "\"goodbye\":{"
									+ "\"this\":\"hello\","
									+ "\"that\":[\"moon\","
												+ "{},"
												+ "\"hello\""
												+ "]"
								+ "}"
						+ "}";
		try{ 
			StringWriter sw = new StringWriter();
			JsonCleaner thisCleaner = new JsonCleaner(new StringReader(inputJson));
			thisCleaner.cleanDuplicates();
			thisCleaner.write(sw);
			assertEquals("Problem during cleaning process", 
					expectedJson, sw.toString());
		} catch(Exception e) {
			e.printStackTrace();
			fail("No exceptions should occur during construction");
		}		
	}
	
	@Test
	public void testCleanExampleGiven() {
		String inputJson = "{"
							+ "\"wholesaler\":\"US Foods\","
							+ "\"delivered\":\"2015-06-19T05:15:00-0500\"," 
							+ "\"contacts\":["
									+ "{\"wholesaler\":\"US Foods\",\"name\":\"John Lederer\"}," 
									+ "{\"wholesaler\":\"Sysco\",\"name\":\"Bill Delaney\"}"
								+ "]"
							+ "}";
		String expectedJson = "{"
								+ "\"wholesaler\":\"US Foods\","
								+ "\"delivered\":\"2015-06-19T05:15:00-0500\"," 
								+ "\"contacts\":["
										+ "{\"name\":\"John Lederer\"}," 
										+ "{\"name\":\"Bill Delaney\"}"
									+ "]"
								+ "}";
		try{ 
			StringWriter sw = new StringWriter();
			JsonCleaner thisCleaner = new JsonCleaner(new StringReader(inputJson));
			thisCleaner.cleanDuplicates();
			thisCleaner.write(sw);
			assertEquals("Problem during cleaning process", 
					expectedJson, sw.toString());
		} catch(Exception e) {
			e.printStackTrace();
			fail("No exceptions should occur during construction");
		}		
	}

}
