import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class AttributeListTest {

	@Test
	public void test() {
		String miles = "Miles";
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		attributes.add(new Attribute("Mile", new ArrayList<String>()));
		assertTrue(Attribute.isNameTaken(attributes, "Mile"));
		assertTrue(Attribute.isNameTaken(attributes, "Miles"));
		assertEquals("Miles", miles);
	}
}
