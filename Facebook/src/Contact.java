import java.util.ArrayList;

public class Contact {
	private ArrayList<String> names = new ArrayList<String>();
	private int pID;
	public static int countID = 1;
	private ContactType type = ContactType.DEFAULT;

	ArrayList<String> email = null;

	Contact(String first, String last) {
		names.add(first);
		names.add(last);
		this.pID = new Integer(countID++);
	}

	Contact(String first, String middle, String last) {
		names.add(first);
		names.add(middle);
		names.add(last);
		this.pID = new Integer(countID++);
	}
	
	Contact(String[] split, int id)
	{
		for(String s : split)
		{
			names.add(s);
		}
		this.pID = id;
	}
	
	public void setType(ContactType type)
	{
		this.type = type;
	}
	
	public Contact(String[] split) {
		for(String s : split)
		{
			names.add(s);
		}
		this.pID = new Integer(countID++);
	}

	public int getpID()
	{
		return this.pID;
	}
	
	public String fullName()
	{
		String full = new String();
		for(String s: names)
		{
			full += s + " ";
		}
		full = full.trim();
		return full;
	}

	public String toString() {
		String result = this.fullName();
		if (email != null) {
			for (String address : email) {
				result += " " + address + " ";
			}
		}
		return result + " " + this.pID + " " + this.type;
	}

	public void printContact() {
		System.out.println(this.toString());
	}

	public void setEmail(String newEmail) {
		if (this.email == null) {
			this.email = new ArrayList<String>();
		}
		this.email.add(newEmail);
	}
}
