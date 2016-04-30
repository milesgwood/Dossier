import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
	static String headerTag = "th";
	static String dataTag = "td";
	static String listTag = "ul";
	static String listItemTag = "li";
	static String home = null;
	static ArrayList<Attribute> attributes = new ArrayList<Attribute>();
	private AttributeList newAttribute;
	private static Elements links = null;

	// This class gets passed the index.htm file and finds the rest of the
	// needed files
	public static ArrayList<Attribute> parseMain(String path) throws IOException {
		File input = new File(path);
		Document doc = Jsoup.parse(input, "UTF-8");
		Elements lists = doc.getElementsByTag(listItemTag);
		Elements names = doc.getElementsByTag(headerTag);

		if (home == null) {
			home = path.substring(0, path.lastIndexOf('/') + 1);
		}
		parseListItems(lists); // ul li lists
		parseSingleTextFields(names); // divs and data

		if (links == null) {
			links = doc.select("a[href]");
			// System.out.println(links.size());
			for (Element link : links) {
				if (link.parent().parent().parent().className().equals("nav")) {
					parseLink(link); // links in nav div
				}
			}
		}
		// System.out.println(Attribute.allNames(attributes).toString());
		return attributes;
	}

	public static Elements removeLinks(Elements set) {
		Elements newSet = new Elements();
		//System.out.println(set.size());
		for (Element e : set) {
			if (!e.parent().parent().className().equals("nav")) {
				//System.out.println("adding " + e.text());
				newSet.add(e);
			}
		}
		return newSet;
	}

	public static void parseLink(Element link) throws IOException {
		String relHref;
		String path;
		relHref = link.attr("href");
		path = new String(home + relHref);
		File input = new File(home + relHref);
		Document doc = Jsoup.parse(input, "UTF-8");
		Elements lists = doc.getElementsByTag(listItemTag);
	}

	private static void parseSingleTextFields(Elements names) {
		String data;
		String[] splitData;
		String name;
		Element next;
		ArrayList<String> dataList;
		AttributeList newAttribute;

		for (Element e : names) {
			dataList = new ArrayList<String>();
			name = e.text();
			next = e.nextElementSibling();

			if (next.tagName().equals(dataTag)) { // we have a dataTag
				if (next.hasText()) {
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
			if (dataList.size() == 0) {
				return;
			}
			if (!AttributeList.isNameTaken(attributes, name)) {
				newAttribute = new AttributeList(name, dataList);
				attributes.add(newAttribute);
			}
		}
	}

	private static void parseListItems(Elements lists) {
		Element grandparentTr;
		String name = "old";
		String newName = "new";
		ArrayList<String> dataList = new ArrayList<String>();

		for (Element e : lists) {
			grandparentTr = e.parent().parent();
			if (grandparentTr.text().endsWith("Responses")) {
				parsePageConnections(grandparentTr.text());
				continue;
			}
			newName = grandparentTr.firstElementSibling().text();
			if (!newName.equals(name) && dataList.size() > 0 || e.equals(lists.get(lists.size() - 1))) {
				if (!AttributeList.isNameTaken(attributes, name)) {
					attributes.add(new AttributeList(name, dataList));
				}
				dataList = new ArrayList<String>();
			}
			name = new String(newName);
			dataList.add(new String(e.text()));
		}
	}

	private static void parsePageConnections(String text) {
	}

	private static String truncate(String extra) {
		String shortened = new String();
		if (extra.endsWith("s")) {
			shortened = extra.substring(0, extra.length() - 2);
		}
		return shortened;
	}

	private static void personTags(AttributeList at) {
		for (String person : at.list) {
			person.contains("(Cousin)");

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