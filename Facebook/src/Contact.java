import java.util.ArrayList;

public class Contact {
	private String first = null;
	private String middle = null;
	private String last = null;
	private int pID;
	public static int countID = 1;
	private ContactType type = ContactType.DEFAULT;

	ArrayList<String> email = null;

	Contact(String first, String last) {
		this.first = first;
		this.last = last;
		this.pID = new Integer(countID++);
	}

	Contact(String first, String middle, String last) {
		this.first = first;
		this.middle = middle;
		this.last = last;
		this.pID = new Integer(countID++);
	}
	
	Contact(String[] split, int id)
	{
		this.first = split[0];
		this.pID = id;
		if(split.length == 2)
		{
			this.last = split[1];
		}
		if(split.length > 2)
		{
			this.middle = split[1];
			this.last = split[2];
		}
		Parser.contacts.add(this);
	}
	
	public void setType(ContactType type)
	{
		this.type = type;
	}
	
	public Contact(String[] split) {
		this.first = split[0];
		this.pID = new Integer(countID++);
		if(split.length == 2)
		{
			this.last = split[1];
		}
		if(split.length > 2)
		{
			this.middle = split[1];
			this.last = split[2];
		}
		Parser.contacts.add(this);
	}

	public int getpID()
	{
		return this.pID;
	}
	
	public String fullName()
	{
		if(this.middle != null)
		{
			return this.first + " " + this.middle + " " + this.last;
		}
		return this.first + " " + this.last;
	}

	public String toString() {
		String result = "";
		result = first;
		if (middle != null) {
			result += " " + middle;
		}
		result += " " + last;
		if (email != null) {
			for (String address : email) {
				result += " " + address + " ";
			}
		}
		return result + " " + this.pID;
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
