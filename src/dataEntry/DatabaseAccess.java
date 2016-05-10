package dataEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseAccess {
	
	public static ArrayList<ArrayList<String>> getAllOwnerInfo()
	{
		Connection c;
		PreparedStatement stmt;
		ResultSet rs;
		String head;
		ArrayList<String> headers = new ArrayList<String>();
		ArrayList<String> info = new ArrayList<String>();
		ArrayList<ArrayList<String>> allInfo = new ArrayList<ArrayList<String>>();
		
		c = DatabaseManagment.getConnection();
		try {
			stmt = c.prepareStatement("select type, count(info) from owner_info group by type order by count(info), type;");
			rs = stmt.executeQuery();
			while(rs.next())
			{
				head = rs.getString(1);
				headers.add(head);
			}
			rs.close();
			stmt.close();
			c.close();
			String noTags;
			for(String h: headers)
			{
				//noTags = h.substring(4,h.lastIndexOf('<'));
				info.add(getInfoList(h));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		allInfo.add(headers);
		allInfo.add(info);
		return allInfo;
	}
	
	public static String getFullName(int id)
	{
		Connection c;
		PreparedStatement stmt;
		ResultSet rs;
		String name = "";
		String middle, last;
		
		c = DatabaseManagment.getConnection();
		try {
			stmt = c.prepareStatement("select first, middle, last from contacts where id ==  $1");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			name = rs.getString(1);
			middle = rs.getString(2);
			last = rs.getString(3);
			name += middle != null? " " + middle : "";
			name += last != null?   " " + last   : "";
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
	

	public static String getInfoSingle(String type) {
		Connection c;
		PreparedStatement stmt;
		ResultSet rs;
		String info = "";
		
		c = DatabaseManagment.getConnection();
		try {
			stmt = c.prepareStatement("select info from owner_info where type == ? ");
			stmt.setString(1, type);
			rs = stmt.executeQuery();
			info = rs.getString(1);
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info != null ? info: "Unknown";
	}


	public static String getInfoList(String type) {
		Connection c;
		PreparedStatement stmt;
		ResultSet rs;
		String info = "";
		
		c = DatabaseManagment.getConnection();
		try {
			stmt = c.prepareStatement("select info from owner_info where type == ? ");
			stmt.setString(1, type);
			rs = stmt.executeQuery();
			
			while(rs.next())
			{
				info += rs.getString(1)+ "<br>";
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info != null ? info: "Unknown";
	}
}
