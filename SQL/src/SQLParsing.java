import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SQLParsing {

	public static void main(String[] args) {

		BufferedReader br = null;

		try {
			String sCurrentLine, result;
			StringBuilder f = new StringBuilder();
			br = new BufferedReader(new FileReader("create.table.contacts.sql"));
			while ((sCurrentLine = br.readLine()) != null) {
				f.append(" \" " + sCurrentLine + "\" + \n");
			}
			result = f.substring(0, f.length() - 3);
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();}}}}
