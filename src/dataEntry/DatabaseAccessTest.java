package dataEntry;

import static org.junit.Assert.*;

import org.junit.Test;

public class DatabaseAccessTest {

	@Test
	public void testGettingMultiplierFromString() {
		assertEquals(DatabaseAccess.getMulitplierNum("OWNER"), 0);
		assertEquals(DatabaseAccess.getMulitplierNum("DEFAULT"), 1);
	}
}
