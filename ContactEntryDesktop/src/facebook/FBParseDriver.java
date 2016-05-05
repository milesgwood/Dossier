package facebook;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class FBParseDriver {

	public static void main(String[] args) throws IOException {
		Parser.parseMain("/cs/home/stu/greatwmc/Downloads/fb/index.htm");
		ParseCleaner.clean();
		//printAllAttributes(Parser.attributes);
		printAllContacts(Parser.contacts);
		//printAllMessages(Parser.messageList);
		
		
		//ContactsView.makeContactsGraphView(Parser.contacts);
		Parser.getpID("Miles");
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
