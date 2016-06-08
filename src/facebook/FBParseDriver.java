package facebook;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

//My package imports
import dataEntry.DatabaseManagment;

public class FBParseDriver {

	public static void main(String[] args) {
		try {
		//Create info objects
			Parser.parseMain("/home/vice6/Downloads/FBMiles/index.htm");
			ParseCleaner.clean();
			printAllAttributes(Parser.attributes);
		
		//Delete Tables if needed and recreate them
			//DatabaseManagment.deleteTable("contacts");
			//DatabaseManagment.deleteTable("fb");
			//DatabaseManagment.deleteTable("messages");
			//DatabaseManagment.deleteTable("typeMultipliers");
			//DatabaseManagment.deleteTable("owner_info");
			//DatabaseManagment.deleteAll();
			DatabaseManagment.createDossierTables();
			DatabaseManagment.dossierConnect(false);
			
			//add owner info
			populateOwnerAttributes(Parser.attributes);
			
		
		//Create the fb table with contacts
			//printAllContacts(Parser.contacts);
			populateFbTable();
		
		//Create message table
			//printAllMessages(Parser.messageList);
			populateMessages();
			DatabaseManagment.dossierCloseConnection();
			DatabaseManagment.dossierConnect(false);
			
		//Fill contacts with fb info and scores based on messages
			DatabaseManagment.populateContactsAndScores();
			
			// ContactsView.makeContactsGraphView(Parser.contacts);
			DatabaseManagment.dossierCloseConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void populateOwnerAttributes(ArrayList<Attribute> attributes) {
		for(Attribute a: attributes)
		{
			if(a.getName().equals("Friend")) continue;
			for(String info: a.list)
			{
				DatabaseManagment.addOwnerInfo(a.getName(), info, 1);
			}
		}
	}

	private static void populateMessages() {
		//printAllMessages(Parser.messageList);
		for(Message m : Parser.messageList)
		{
			DatabaseManagment.addMessages(m.getThreadID(), m.getMessageID(), m.messageSenderID(), m.getDate(), m.getMessage());
		}
	}

	public static void populateFbTable(){
		// printAllContacts(Parser.contacts);
		addAllContactsToDatabase(Parser.contacts);
		DatabaseManagment.defaultTypeMultipliers();
		System.out.println("Size of contacts list: - " +  Parser.contacts.size());
	}

	private static void addAllContactsToDatabase(HashSet<Contact> contacts) {
		ArrayList<Integer> failed = new ArrayList<Integer>();
		for (Contact c : contacts) {
			if (!DatabaseManagment.addFBContacts(c.getpID(), c.getListofNames(), c.getType().toString(),
					c.getEmail())) {
				failed.add(c.getpID());
			}
		}
		System.out.println(failed.size() + " failed");
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
