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
		return this.list.toString();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
