package facebook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

//My package imports
import dataEntry.DatabaseManagment;

public class FBParseDriver {

	public static void main(String[] args) throws IOException {
		Parser.parseMain("/cs/home/stu/greatwmc/Downloads/fb/index.htm");
		ParseCleaner.clean();
		//printAllAttributes(Parser.attributes);
		//printAllContacts(Parser.contacts);
		Contact c = Parser.contacts.iterator().next();
		//printAllMessages(Parser.messageList);
		//deleteTable();
		addAllContactsToDatabase(Parser.contacts);
		//ContactsView.makeContactsGraphView(Parser.contacts);
	}
	
	private static void addAllContactsToDatabase(HashSet<Contact> contacts) {
		ArrayList<Integer> failed = new ArrayList<Integer>();
		for(Contact c: contacts)
		{
			if(!DatabaseManagment.addFBContacts(c.getListofNames(), c.getType().toString(), c.getEmail()))
			{
				failed.add(c.getpID());
			}
		}
		System.out.println(failed.size() + "failed");
	}

	private static void printAllMessages(ArrayList<Message> messageList) {
		for(Message m : messageList)
		{
			m.printMessage();
		}
		
	}

	public static void printAllAttributes(ArrayList<Attribute> attributes)
	{
		for(Attribute a : attributes)
		{
			//Whole list printed
			a.printAttribute();
			//Or just the header names
			//System.out.println(a.getName());
		}
	}
	
	public static void printAllContacts(HashSet<Contact> contacts)
	{
		for(Contact c : contacts)
		{
			c.printContact();
		}
	}
}
