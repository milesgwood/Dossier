package dataEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseInfoUpdate {
	
	public static void updateAttributeWithString(String attribute, String value, int id)
	{
		Connection c;
		PreparedStatement stmt;
		String sql;
		
		sql = "UPDATE contacts SET " + attribute +  " =?  where id ==  ? ;";
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
	
	public static void updateMultiplier(String oldType, String newType, int oldVal, int newVal)
	{
		Connection c;
		PreparedStatement stmt;
		String deleteSQL, addSQL, updateSQL;
		
		deleteSQL = "DELETE FROM typemultipliers WHERE type = '?';";
		addSQL = "INSERT INTO typemultipliers VALUES('" + newType + "' , " + newVal + ");" ; 
		updateSQL = "UPDATE contacts SET type = ?, multiplier = ? WHERE type = ?";
		c = DatabaseManagment.getConnection();
		try {
			//TO DO
			stmt = c.prepareStatement("NOTHING");
			stmt.executeUpdate();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
