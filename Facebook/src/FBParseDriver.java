import java.io.IOException;

public class FBParseDriver {

	public static void main(String[] args) throws IOException {
		Parser.parseMain("/home/vice6/Downloads/FBMiles/index.htm");
		ParseCleaner.clean();
		//ContactsView.makeContactsGraphView(Parser.contacts);
		Parser.getpID("Miles");
	}
}
