import java.util.ArrayList;

public class Contact {
	String first = null;
	String middle = null;
	String last = null;

	ArrayList<String> email = null;

	Contact(String first, String last) {
		this.first = first;
		this.last = last;
	}

	Contact(String first, String middle, String last) {
		this.first = first;
		this.middle = middle;
		this.last = last;
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
