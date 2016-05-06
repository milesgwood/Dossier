package facebook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

//My package imports
import dataEntry.DatabaseManagment;

public class FBParseDriver {

	public static void main(String[] args) {
		// printAllAttributes(Parser.attributes);
		try {
		//Create info objects
			//Parser.parseMain("/home/vice6/Downloads/FBMiles/index.htm");
			//ParseCleaner.clean();
		
		//Delete Tables if needed and recreate them
			//DatabaseManagment.deleteTable("contacts");
			//DatabaseManagment.deleteTable("fb");
			//DatabaseManagment.deleteTable("messages");
			//DatabaseManagment.deleteTable("typeMultipliers");
			DatabaseManagment.createDossierTables();
			//DatabaseManagment.dossierConnect(false);
		
		//Create the fb table with contacts
			// printAllContacts(Parser.contacts);
			//populateFbTable();
		
		//Create message table
			//printAllMessages(Parser.messageList);
			//populateMessages();
			
			// ContactsView.makeContactsGraphView(Parser.contacts);
			//DatabaseManagment.dossierCloseConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void populateMessages() {
		//printAllMessages(Parser.messageList);
		for(Message m : Parser.messageList)
		{
			DatabaseManagment.addMessages(m.getThreadID(), m.getMessageID(), m.messageSenderID(), m.getDate(), m.getMessage());
		}
	}

	public static void populateFbTable() throws IOException {
		// printAllContacts(Parser.contacts);
		addAllContactsToDatabase(Parser.contacts);
		System.out.print(Parser.contacts.size());
	}

	private static void addAllContactsToDatabase(HashSet<Contact> contacts) {
		ArrayList<Integer> failed = new ArrayList<Integer>();
		for (Contact c : contacts) {
			if (!DatabaseManagment.addFBContacts(c.getpID(), c.getListofNames(), c.getType().toString(),
					c.getEmail())) {
				failed.add(c.getpID());
			}
		}
		System.out.println(failed.size() + "failed");
	}

	private static void printAllMessages(ArrayList<Message> messageList) {
		for (Message m : messageList) {
			m.printMessage();
		}

	}

	public static void printAllAttributes(ArrayList<Attribute> attributes) {
		for (Attribute a : attributes) {
			// Whole list printed
			a.printAttribute();
			// Or just the header names
			// System.out.println(a.getName());
		}
	}

	public static void printAllContacts(HashSet<Contact> contacts) {
		for (Contact c : contacts) {
			c.printContact();
		}
	}
}
