package pruningData;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import dataEntry.DatabaseManagment;

public class DeleteMeaninglessTuples {
	
	public static void deletePersonEvents()
	{
		Connection c;
		Statement stmt;
		
		try {
			c = DatabaseManagment.getConnection();
			stmt = c.createStatement();
			stmt.execute("DELETE FROM people_at_events WHERE eID < 1 OR pID < 1 ;");
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
