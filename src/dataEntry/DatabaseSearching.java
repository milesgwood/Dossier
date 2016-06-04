package dataEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DatabaseSearching {

	public static List<String> getAllNames()
	{
		ArrayList<String> names = new ArrayList<String>();
		
		Connection c;
		PreparedStatement stmt;
		ResultSet rs;
		
		String name = "";
		String middle, last;
		
		c = DatabaseManagment.getConnection();
		try {
			stmt = c.prepareStatement("select first, middle, last from contacts");
			rs = stmt.executeQuery();
			
			while(rs.next())
			{
				name = rs.getString(1);
				middle = rs.getString(2);
				last = rs.getString(3);
				name += middle != null? " " + middle : "";
				name += last != null?   " " + last   : "";
				names.add(name);
				//System.out.println(name);
			}
			
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return names;
	}

}
