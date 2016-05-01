import java.util.ArrayList;

public class Contact {
	private String first = null;
	private String middle = null;
	private String last = null;
	private int pID;
	public static int countID = 1;

	ArrayList<String> email = null;

	Contact(String first, String last) {
		this.first = first;
		this.last = last;
		this.pID = countID++;
	}

	Contact(String first, String middle, String last) {
		this.first = first;
		this.middle = middle;
		this.last = last;
		this.pID = countID++;
	}
	
	public Contact(String[] split) {
		if(split.length == 2)
		{
			Contact c = new Contact(split[0], split[1]);
			Parser.contacts.add(c);
		}
		if(split.length == 3)
		{
			Contact c = new Contact(split[0], split[1], split[2]);
			Parser.contacts.add(c);
		}
	}

	public int getpID()
	{
		return this.pID;
	}
	
	public String fullName()
	{
		if(this.middle != null)
		{
			return this.first + " " + this.middle +"  " + this.last;
		}
		return this.first + " " + this.last;
	}

	public String toString() {
		String result = "";
		result = first + " " + last;
		if (middle != null) {
			result += " " + middle;
		}
		if (email != null) {
			for (String address : email) {
				result += " " + address + " ";
			}
		}
		return result;
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
