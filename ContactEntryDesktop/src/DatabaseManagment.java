import java.sql.*;

public class DatabaseManagment {
	public static void main(String args[]) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:People.sqlite");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql =  " CREATE TABLE IF NOT EXISTS People" + 
					 " (ID INT PRIMARY KEY NOT NULL, " + 
					 " FIRST CHAR(15) NOT NULL, " + 
					 " LAST CHAR(15)," + 
					 " AGE INT, " + 
					 " SEX CHAR CHECK(SEX = 'Y' OR SEX = 'M')," + 
					 " ADDRESS CHAR(50), " + 
					 " STATE CHAR(2), " + 
					 " RANK INT," + 
					 " INTERESTS TEXT);";
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}
	
	public static void deleteTable() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:People.sqlite");
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			String sql =  "DROP TABLE people;";
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table Dropped");
	}
}