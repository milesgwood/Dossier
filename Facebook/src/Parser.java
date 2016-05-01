import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
	static String headerTag = "th";
	static String dataTag = "td";
	static String listTag = "ul";
	static String listItemTag = "li";
	static String friends = "/html/friends.htm";
	static String messages = "/html/messages.htm";
	static String contact = "/html/contact_info.htm";
	static String home = null;
	private static Elements links = null;
	
	static ArrayList<Attribute> attributes = new ArrayList<Attribute>();
	static HashSet<Contact> contacts = new HashSet<Contact>();
	static ArrayList<Message> messageList = new ArrayList<Message>();

	public static void clearAttribute() {
		attributes = new ArrayList<Attribute>();
		Attribute.resetCount();
	}

	// This class gets passed the index.htm file and finds the rest of the
	// needed files
	public static ArrayList<Attribute> parseMain(String path) throws IOException {
		File input = new File(path);
		Document doc = Jsoup.parse(input, "UTF-8");
		Elements names = doc.getElementsByTag(headerTag);

		if (home == null) {
			home = path.substring(0, path.lastIndexOf('/') + 1);
		}
		if (links == null) {
			links = doc.select("div.nav > ul > li > a[href]");
		}
		parseContentBodyLists(doc);
		parseSingleTextFields(names); // divs and data
		// System.out.println(Attribute.allNames(attributes).toString());
		input = new File(home + friends);
		doc = Jsoup.parse(input, "UTF-8");
		parseFriends(doc);
		input = new File(home + messages);
		doc = Jsoup.parse(input, "UTF-8");
		parseMessages(doc);
		return attributes;
	}

	public static Elements removeLinks(Elements set) {
		Elements less = new Elements();
		for (Element e : set) {
			if (!e.parent().parent().className().equals("nav")) {
				// System.out.println("adding " + e.text());
				less.add(e);
			}
		}
		return less;
	}

	public static void parseFriends(Document doc) {
		ArrayList<String> friends = new ArrayList<String>();
		Elements friendsList = doc.select("div.contents > div > ul > li");
		for (Element e : friendsList) {
			friends.add(e.text());
			parseContacts(e.text());
		}
		attributes.add(new Attribute("Friend", friends));
		/**for (Contact c : contacts) {
			c.printContact();
		}**/
	}

	public static void parseMessages(Document doc) {

		int pID;
		String name;
		String date;
		String message;
		int threadId = 0;
		
		Elements users = doc.select("div.message_header > span.user");
		Element oldThread = users.first().parent();
		Element newThread;
		
		for (Element e : users) {
			if (!e.text().endsWith(".com")) {
				name = e.text();
				date = e.nextElementSibling().text();
				pID = getpID(name);
				if(name.equals("Miles Greatwood"))
				{
					pID = 0;
				}
				message = e.parent().parent().nextElementSibling().text();
				newThread = e.parent().parent().parent();
				if(!newThread.equals(oldThread))
				{
					threadId++;
					oldThread = newThread;
				}
				messageList.add(new Message(pID, name,  date, message, threadId));
			}
		}
	}
	public static int getpID(String name)
	{
		for(Contact c :contacts)
		{
			if(c.fullName().equals(name))
			{
				return c.getpID();
			}
		}
		Contact newC = new Contact(name.split(" "));
		return newC.getpID();
	}

	private static void parseContacts(String name) {
		boolean hasEmail = name.endsWith(")");
		String[] info = name.split(" ");
		Contact newContact;
		if (hasEmail) {
			if (info.length > 3) {
				newContact = new Contact(info[0], info[1], info[2]);
				newContact.setEmail(info[3].substring(1, info[3].length() - 1));
				contacts.add(newContact);
			} else {
				newContact = new Contact(info[0], info[1]);
				newContact.setEmail(info[2].substring(1, info[2].length() - 1));
				contacts.add(newContact);
			}
		} else {
			if (info.length > 2) {
				contacts.add(new Contact(info[0], info[1], info[2]));
			} else {
				contacts.add(new Contact(info[0], info[1]));
			}
		}
	}

	// Format is "div.contents > div > table > tbody > tr > td > ul > li"
	public static void parseContentBodyLists(Document doc) {
		ArrayList<String> dataList;
		Element grandparentTr;
		String name, newName;

		name = "a09sdfysdofa";
		newName = "d98fa6osdt";
		dataList = new ArrayList<String>();
		Elements listItems = doc.select("div.contents > div > table > tbody > tr > td > ul > li");

		for (Element e : listItems) {
			grandparentTr = e.parent().parent();
			newName = grandparentTr.firstElementSibling().text();
			if (!newName.equals(name) && dataList.size() > 0 || e.equals(listItems.last())) {
				if (!Attribute.isNameTaken(attributes, name)) {
					attributes.add(new Attribute(name, dataList));
				}
				dataList = new ArrayList<String>();
			}
			name = new String(newName);
			dataList.add(new String(e.text()));
		}
		if (!Attribute.isNameTaken(attributes, name) && !dataList.isEmpty()) {
			attributes.add(new Attribute(name, dataList));
		}
	}

	// Format is "div.contents > div > table > tbody > tr > td >span.meta > ul >
	// li"
	public static void parseMetaLists(Document doc) {
		ArrayList<String> dataList;
		Element greatGrandparentTr;
		String name, newName;

		name = "a09sdfysdofa";
		newName = "d98fa6osdt";
		dataList = new ArrayList<String>();
		Elements listItems = doc.select("div.contents > div > table > tbody > tr > td >span.meta > ul > li");

		for (Element e : listItems) {
			greatGrandparentTr = e.parent().parent().parent();
			newName = greatGrandparentTr.firstElementSibling().text();
			if (!newName.equals(name) && dataList.size() > 0 || e.equals(listItems.last())) {
				if (!Attribute.isNameTaken(attributes, name)) {
					attributes.add(new Attribute(name, dataList));
				}
				dataList = new ArrayList<String>();
			}
			name = new String(newName);
			dataList.add(new String(e.text()));
		}
		if (!Attribute.isNameTaken(attributes, name) && !dataList.isEmpty()) {
			attributes.add(new Attribute(name, dataList));
		}
	}

	// This splits String fields on commas
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
			if (!Attribute.isNameTaken(attributes, name)) {
				attributes.add(new Attribute(name, dataList));
			} else {

			}
		}
	}

	private static void personTags(Attribute at) {
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