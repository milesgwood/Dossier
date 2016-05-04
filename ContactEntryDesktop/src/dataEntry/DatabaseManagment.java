package dataEntry;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManagment {
	public static void main(String args[]) {
		//deleteTable();
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Dossier.sqlite");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql =    " CREATE TABLE IF NOT EXISTS People" + 
					 " (ID INT PRIMARY KEY NOT NULL, " + 
					 " FIRST VARCHAR(15) NOT NULL, " + 
					 " MIDDLE VARCHAR(15)," + 
					 " LAST VARCHAR(15)," + 
					 " ALIAS VARCHAR (15)," + 
					 " AGE INT, " + 
					 " SEX CHAR CHECK(SEX = 'Y' OR SEX = 'M')," + 
					 " ADDRESS VARCHAR(50), " + 
					 " STATE CHAR(2), " + 
					 " MULTIPLIER INT," + 
					 " TYPE VARCHAR (15)," + 
					 " SCORE INT," + 
					 " PHONE VARCHAR(15)," + 
					 " EMAIL VARCHAR(30)," + 
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
			String sql =  "DROP TABLE contacts;";
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table Dropped");
	}
	
	public static void addFBContacts(int pid, ArrayList<String> names, String type, String email) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:People.sqlite");
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			String sqlHead =  "INSERT INTO contacts(ID, FIRST, TYPE";
			String sqlValues = "(" + pid + "," + names.get(0) + "," + type;
			if(names.size() > 1)
			{
				sqlHead += " , MIDDLE";
				sqlValues += "," + names.get(1);
			}
			if(names.size() > 2)
			{
				sqlHead += " , LAST";
				sqlValues += "," + names.get(2);
			}
			if(email != null)
			{
				sqlHead += " , EMAIL";
				sqlValues += "," + email;
			}
			sqlHead += ")";
			sqlValues += ");";
			System.out.println(sqlHead + sqlValues);
			//stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table Dropped");
	}
}