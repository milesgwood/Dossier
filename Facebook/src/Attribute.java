public abstract class Attribute {
	private static int attCount = 0;
	private String attName;
	private int elementID;

	Attribute(String name) {
		this.attName = name;
		this.elementID = this.getCount();
		attCount++;
	}

	public int getCount() {
		return attCount;
	}

	public String getName() {
		return this.attName;
	}

	public abstract String getInfo();

	public int getID() {
		return this.elementID;
	}
	
	public abstract String toString();
}
