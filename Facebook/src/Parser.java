import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {

	static String headerTag = "th";
	static String dataTag = "td";
	static String listTag = "ul";
	static String listItemTag = "li";
	static ArrayList<Attribute> attributes;

	public static void main(String[] args) throws IOException {
		File input = new File(args[0]);
		Document doc = Jsoup.parse(input, "UTF-8");
		Elements lists = doc.getElementsByTag(listItemTag);
		attributes = new ArrayList<Attribute>();
		parseListItems(lists);

		// Parse the rest of the divs which have a header and data
		Elements names = doc.getElementsByTag(headerTag);
		parseSingleTextFields(names);
	}

	private static void parseSingleTextFields(Elements names) {
		String data;
		String[] splitData;
		String name;
		Element next;
		ArrayList<String> dataList;

		for (Element e : names) {
			dataList = new ArrayList<String>();
			name = e.text();
			next = e.nextElementSibling();

			if (next.tagName().equals(dataTag)) { // we have a dataTag
				if (next.children().hasText()) {
					data = next.text();
					splitData = data.split(", ");
					for (String s : splitData) {
						dataList.add(s);
					}
				} else if (next.childNodeSize() > 1) // We have a straight list
				{
					dataList = parseStraightList(next);
				}
			}
			if (dataList.size() == 1) {
				attributes.add(new BackgroundAtt(name, dataList.get(0)));
			} else {
				attributes.add(new AttributeList(name, dataList));
			}
		}
	}

	private static void parseListItems(Elements lists) {
		Element grandparentTr;
		String name = "old";
		String newName = "new";
		ArrayList<String> dataList = new ArrayList<String>();
		
		for (Element list : lists) {
			grandparentTr = list.parent().parent();
			if (grandparentTr.text().startsWith("Profile Contact Info")) {
				continue;
			}
			newName = grandparentTr.firstElementSibling().text();
			if(!newName.equals(name) && dataList.size() > 0)
			{
				attributes.add(new AttributeList(name, dataList));
				name = new String(newName);
				dataList = new ArrayList<String>();
			}
			dataList.add(new String(list.text()));
		}
	}

	private static ArrayList<String> parseStraightList(Element next) {
		Elements childElements;
		ArrayList<String> list = new ArrayList<String>();
		childElements = next.children();
		for (Element e : childElements) {
			list.add(e.text());
		}
		return list;
	}
}