
public class Message {
	private int pID;
	private String name;
	private String date;
	private String message;
	private static int mIDcount = 0;
	private int mID;
	private int threadID;
	
	Message(int pID, String name,  String date, String message, int threadID)
	{
		this.pID = pID;
		this.name = name;
		this.date = date;
		this.message = message;
		this.threadID = threadID;
		this.mID = mIDcount++;
	}
	
	public String toString()
	{
		String result;
		result = this.threadID + ":" + this.mID + " " + this.name + " " + date + " ";
		if(this.message.length() > 15)
		{
			result += message.substring(0, 14);
		}
		else
		{
			result += message;
		}
		return result;
	}
	
	public int getThreadID()
	{
		return this.threadID;
	}
	public int getMessageID()
	{
		return this.mID;
	}
	public String getMessage()
	{
		return this.message;
	}
	public String getDate()
	{
		return this.date;
	}
	public int messageSenderID()
	{
		return Parser.getpID(this.name);
	}
	public String messageSenderName()
	{
		return this.name;
	}
}
