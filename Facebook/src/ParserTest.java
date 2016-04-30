import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class ParserTest {

	private static Document doc;
	private static Elements links, temp;

	public static void setup(String path) {
		try {
			File input = new File(path);
			doc = Jsoup.parse(input, "UTF-8");
			if (links == null) {
				links = new Elements();
				temp = doc.select("a[href]");
				// System.out.println(temp.size());
				for (Element link : temp) {
					if (link.parent().parent().parent().className().equals("nav")) {
						links.add(link);
						// System.out.println(links.size());
					}
				}
				assertEquals(links.size(), 14);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testContactInfo() {
		setup("/home/vice6/Downloads/FBMiles/html/contact_info.htm");
		assertEquals(links.size(), 14);
		Elements items = doc.getElementsByTag(Parser.listItemTag);
		assertEquals(32, items.size());
		items = Parser.removeLinks(items);
		assertEquals(17, items.size());
		// int i = 0;
		// for (Element item : items) {
		// System.out.println(i++ + " " + item.text());
		// }
	}

	@Test
	public void testParser() throws IOException {
		ArrayList<Attribute> attributes = Parser.parseMain("/home/vice6/Downloads/FBMiles/index.htm");
		ArrayList<String> names = Attribute.allNames(attributes);
		assertEquals(names.size(), 25);
		assertTrue(names.contains("Pages You Admin"));
		assertTrue(names.contains("Hometown"));
		assertTrue(names.contains("Apps"));
		assertTrue(names.contains("Groups"));
	}

	@Test
	public void testParserHomeVariable() throws IOException {
		ArrayList<Attribute> attributes = Parser.parseMain("/home/vice6/Downloads/FBMiles/index.htm");
		// System.out.println(Parser.home);
		// System.out.println("/home/vice6/Downloads/FBMiles/");
		assertEquals(Parser.home, "/home/vice6/Downloads/FBMiles/");
	}

	@Test
	public void testSecondvalue() throws IOException {
		String path = new String("/home/vice6/Downloads/FBMiles/html/contact_info.htm");
		File input = new File(path);
		Document doc = Jsoup.parse(input, "UTF-8");
		Elements e = doc.getElementsByClass("nav");
		int i = 0;
		Elements e2, e3, e4;
		Element single = e.get(0);
		Elements listItems = single.getElementsByTag("li");
		for (Element item : listItems) {
			assertEquals(e.size(), 1);
			// System.out.println(i++ + " " + item.toString());
		}
		assertEquals(listItems.size(), 15);

	}
}
