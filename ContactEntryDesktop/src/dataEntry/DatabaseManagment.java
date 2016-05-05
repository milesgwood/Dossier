package dataEntry;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManagment {
	public static void createDossierAndContact() {
		//deleteTable();
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Dossier.sqlite");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql =    " CREATE TABLE IF NOT EXISTS contacts" + 
					 " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + 
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
			c = DriverManager.getConnection("jdbc:sqlite:Dossier.sqlite");
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
	
	public static boolean addFBContacts(ArrayList<String> names, String type, String email) {
	createDossierAndContact();
		Connection c = null;
		Statement stmt = null;
		String sql = "";
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Dossier.sqlite");
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			String sqlHead =  "INSERT INTO contacts(FIRST, TYPE";
			String sqlValues = "('" + names.get(0) + "', '" + type + "'";
			if(names.size() > 2)
			{
				sqlHead += " , MIDDLE";
				sqlValues += ", '" + names.get(1) +"'";
				sqlHead += " , LAST";
				sqlValues += ", '" + names.get(2) + "'";
			}
			else if(names.size() > 1)
			{
				sqlHead += " , LAST";
				sqlValues += ", '" + names.get(1) + "'";
			}
			if(email.length() > 0)
			{
				sqlHead += " , EMAIL";
				sqlValues += ", '" + email + "'";
			}
			sqlHead += ") VALUES ";
			sqlValues += ");";
			sql = sqlHead + sqlValues;
			
			//System.out.println(sqlHead + sqlValues);
			stmt.executeUpdate(sql);
			
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(sql);
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		//System.out.println("Added" + names.get(0) + " " + pid);
		return true;
	}
}