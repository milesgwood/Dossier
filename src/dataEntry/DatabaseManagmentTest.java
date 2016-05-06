package dataEntry;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import facebook.Parser;

public class DatabaseManagmentTest {

	@Test
	public void testAddMessages() throws IOException {
		Parser.parseMain("/home/vice6/Downloads/FBMiles/index.htm");
		DatabaseManagment.dossierConnect(true);
		assertTrue(DatabaseManagment.addMessages(-3, -7, -5, "Today WAS a ,,, GOOD :day" , "HADFSOFJASID d             a,a,a"));
		assertEquals(Parser.getMessageCount(), 5449);
		assertEquals(Parser.getThreadCount(), 319);
		//DELETE FROM messages WHERE tID == -3;
	}
}
