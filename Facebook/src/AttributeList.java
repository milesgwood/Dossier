import java.util.ArrayList;

public class AttributeList extends Attribute{
	
	ArrayList<String> list;
	AttributeList(String name, ArrayList<String> newAttList)
	{
		super(name);
		this.list = new ArrayList<String>();
		this.list.addAll(newAttList);
	}
	@Override
	public String getInfo() {
		String info = new String();
		for(String s: list)
		{
			info +=s + "\n\t";
		}
		return info;
	}
	
	public static boolean isNameTaken(ArrayList<Attribute> attributes, String name)
	{
		for(Attribute att: attributes)
		{
			if(att.getName().equals(name))
			{
				return true;
			}
		}
		return false;
	}
}
