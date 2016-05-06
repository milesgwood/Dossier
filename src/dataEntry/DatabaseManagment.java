package dataEntry;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import sql.SQLParser;

public class DatabaseManagment {
	static Connection c = null;
	static Statement stmt = null;
	static String sql;
	
	/**
	 * If there are lots of sql statements set autoCommit to false and commit at the end.
	 * @param autoCommit
	 */
	public static void dossierConnect(boolean autoCommit) {
		try {
			if(c != null)
			{
				if(!c.isClosed()) c.close();
			}
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Dossier.sqlite");
			c.setAutoCommit(autoCommit);
			System.out.println("Opened database successfully");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void dossierCloseConnection() {
		try {
			if(!stmt.isClosed()) stmt.close();
			
			if(!c.isClosed()) 
				{
					c.commit();
					c.close();
				}
		} catch (Exception e){
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void createDossierTables() {
		dossierConnect(true);
		try {
			stmt = c.createStatement();
			sql = SQLParser.sqlStringCreation("create.table.contacts.sql");
			stmt.executeUpdate(sql);
			//Create fb table
			stmt = c.createStatement();
			sql = SQLParser.sqlStringCreation("create.table.fb.sql");
			stmt.executeUpdate(sql);
			stmt = c.createStatement();
			sql = SQLParser.sqlStringCreation("create.table.messages.sql");
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Tables created successfully");
	}

	public static void deleteTable(String table) {
		dossierConnect(true);
		try {
			stmt = c.createStatement();
			String sql = "DROP TABLE " + table + ";";
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println(table + " dropped");
	}

	public static boolean addFBContacts(int id, ArrayList<String> names, String type, String email) {
		try {
			stmt = c.createStatement();
			String sqlHead = "INSERT INTO fb(ID, FIRST, TYPE";
			String sqlValues = "( " + id + ", '" + names.get(0) + "', '" + type + "'";
			if (names.size() > 2) {
				sqlHead += " , MIDDLE";
				sqlValues += ", '" + names.get(1) + "'";
				sqlHead += " , LAST";
				sqlValues += ", '" + names.get(2) + "'";
			} else if (names.size() > 1) {
				sqlHead += " , LAST";
				sqlValues += ", '" + names.get(1) + "'";
			}
			if (email.length() > 0) {
				sqlHead += " , EMAIL";
				sqlValues += ", '" + email + "'";
			}
			sqlHead += ") VALUES ";
			sqlValues += ");";
			sql = sqlHead + sqlValues;
			//System.out.println(sqlHead + sqlValues);
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			System.err.println(sql);
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean addMessages(int threadID, int messageID, int messageSenderID, String date,  String message) {
		date = cleanSqlString(date);
		message = cleanSqlString(message);
		try {
			stmt = c.createStatement();
			sql = "INSERT INTO messages(tID, mID, senderID, date, message) VALUES (" + threadID + " , " + messageID +  " , "  + messageSenderID + ", '" + date + "' " + " ,'" + message + "');";
			System.out.println(sql);
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			System.err.println(sql);
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static String cleanSqlString(String s)
	{
		s = s.replace(",", " ");
		s = s.replace("'", "");
		return s;
	}
}