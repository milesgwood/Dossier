package dataEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseInfoUpdate {

	public static void updateAttributeWithString(String attribute, String value, int id) {
		Connection c;
		PreparedStatement stmt;
		String sql;

		sql = "UPDATE contacts SET " + attribute + " =?  where id ==  ? ;";
		c = DatabaseManagment.getConnection();
		try {
			stmt = c.prepareStatement(sql);
			stmt.setString(1, value);
			stmt.setInt(2, id);
			stmt.executeUpdate();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateMultiplier(String type, int newVal) {
		Connection c;
		PreparedStatement stmt;
		String updateContactsSQL, updateTypeMultiplierSQL;
		
		reduceScoresToNoMultiplier(type);
		updateTypeMultiplierSQL = "UPDATE typemultipliers SET multiplier = ? WHERE type = ? ;";
		updateContactsSQL = "UPDATE contacts SET score = (score * (SELECT multiplier FROM typemultipliers WHERE type = ? )) WHERE type = ? ;";
		c = DatabaseManagment.getConnection();
		try {
			stmt = c.prepareStatement(updateTypeMultiplierSQL);
			stmt.setString(2, type);
			stmt.setInt(1, newVal);
			stmt.executeUpdate();
			stmt = c.prepareStatement(updateContactsSQL);
			stmt.setString(1, type);
			stmt.setString(2, type);
			stmt.executeUpdate();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addNewMultiplier(String type, int newVal) {
		Connection c;
		PreparedStatement stmt;
		String sql;
	
		sql = "INSERT INTO typemultipliers (type, multiplier) VALUES(? , ?);";
		c = DatabaseManagment.getConnection();
		try {
			stmt = c.prepareStatement(sql);
			stmt.setString(1, type);
			stmt.setInt(2, newVal);
			stmt.executeUpdate();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * To delete a multiplier we first reduce the contacts with that type by
	 * dividing. Then we delete the type in the type table. Then we set the type
	 * in the contacts to DEFAULT
	 * 
	 * @param multiplier
	 */
	public static void deleteMultiplier(String type) {
		
		reduceScoresToNoMultiplier(type);
		Connection c;
		PreparedStatement stmt;
		String deleteSQL;

		deleteSQL = "DELETE FROM typemultipliers WHERE type = '" + type + "';";

		c = DatabaseManagment.getConnection();
		try {
			stmt = c.prepareStatement(deleteSQL);
			stmt.executeUpdate();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void reduceScoresToNoMultiplier(String type)
	{
		Connection c;
		PreparedStatement stmt;
		String reduceScoreSQL;

		reduceScoreSQL = "UPDATE contacts SET score = score/(SELECT multiplier FROM typemultipliers WHERE type = '"
				+ type + "') WHERE type = '" + type + "';";

		c = DatabaseManagment.getConnection();
		try {
			stmt = c.prepareStatement(reduceScoreSQL);
			stmt.executeUpdate();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
