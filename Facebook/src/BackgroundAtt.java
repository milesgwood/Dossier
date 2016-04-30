
public class BackgroundAtt extends Attribute{
	private String info;
	
	BackgroundAtt(String name, String info)
	{
		super(name);
		this.info = new String(info);
	}

	@Override
	public String getInfo() {
		return this.info;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}}