package dataEntry;

public class ContactIDNotFoundException extends Exception {
	
	ContactIDNotFoundException(String name)
	{
		super("We found no Contact for the name " + name);
		System.out.println("We found no Contact for the name " + name);
	}

}
