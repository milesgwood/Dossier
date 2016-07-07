package events;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dataEntry.DatabaseManagment;

public class EventCreation {
	private static Connection c;
	private static PreparedStatement stmt;
	private static ResultSet rs;

	public static int getHighestEventID() {
		int maxeID = 0;
		try {
			c = DatabaseManagment.getConnection();
			stmt = c.prepareStatement("select max(eID) from events");
			rs = stmt.executeQuery();
			rs.next();
			maxeID = rs.getInt(1);
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maxeID;
	}

	public static void addEventData(String attribute, String value, int eID) {
		try {
			c = DatabaseManagment.getConnection();
			String sql = "UPDATE events SET " + attribute + " =? WHERE eID == ?;";
			stmt = c.prepareStatement(sql);
			stmt.setString(1, value);
			stmt.setInt(2, eID);
			stmt.executeUpdate();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int createNewEvent() {
		int eID;

		eID = getHighestEventID() + 1;
		try {
			c = DatabaseManagment.getConnection();
			stmt = c.prepareStatement("INSERT INTO events(eID) VALUES (?);");
			stmt.setInt(1, eID);
			stmt.execute();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("New eid is " + eID);
		return eID;
	}
	
	public static ArrayList<String> getEventData(int eID) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			c = DatabaseManagment.getConnection();
			String sql = "SELECT * FROM events WHERE eID == ?;";
			stmt = c.prepareStatement(sql);
			stmt.setInt(1, eID);
			rs = stmt.executeQuery();
			rs.next();
			for(int i = 1; i < 9 ; i++)
			{
				System.out.print(rs.getString(i));
				data.add(rs.getString(i));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
}
