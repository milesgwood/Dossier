package dataEntry;

public class ContactIDNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 123L;

	ContactIDNotFoundException(String name)
	{
		super("We found no Contact for the name " + name);
		System.out.println("We found no Contact for the name " + name);
	}

}
