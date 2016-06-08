package dataEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	
	/**
	 * This method finds the relevant ID from a name. A Full name is First Middle Last. Alias is not included.
	 * @param name
	 * @return The ID value
	 * @throws ContactIDNotFoundException 
	 */
	public static int getIdFromFullName(String name) throws ContactIDNotFoundException
	{
		Connection c;
		PreparedStatement stmt;
		ResultSet rs;
		String[] nameSplit = name.split(" ");
		System.out.println("Size of split is " + nameSplit.length);
		for(String s : nameSplit)
		{
			System.out.println(s);
		}
		
		c = DatabaseManagment.getConnection();
		try {
			if(nameSplit.length > 2) { 
				stmt = c.prepareStatement("select id from contacts where first ==  ? AND middle == ? AND last == ?");
				stmt.setString(1, nameSplit[0]);
				stmt.setString(2, nameSplit[1]);
				stmt.setString(3, nameSplit[2]);
			}
			else if(nameSplit.length == 2) 
			{
				stmt = c.prepareStatement("select id from contacts where first ==  ? AND last == ?");
				stmt.setString(1, nameSplit[0]);
				stmt.setString(2, nameSplit[1]);
			}
			else
			{
				stmt = c.prepareStatement("select id from contacts where first ==  ?");
				stmt.setString(1, nameSplit[0]);
			}
			
			rs = stmt.executeQuery();
			
			int id = rs.getInt(1);
			System.out.println(name + "'s id is " + id);
			rs.close();
			stmt.close();
			c.close();
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new ContactIDNotFoundException(name);
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
	
	public static String getOnePieceOfInfo(String attribute, int id) {
		Connection c;
		PreparedStatement stmt;
		ResultSet rs;
		String info = "";
		String sql = "select " + attribute + " from contacts where id == " + id ; 
		
		c = DatabaseManagment.getConnection();
		try {
			stmt = c.prepareStatement(sql);
			rs = stmt.executeQuery();
			rs.next();
			info = rs.getString(1);
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info != null ? info: "";
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
	
	/**
	 * This function finds out how many distinct multiplier classes there are.
	 * Examples are brother, cousin, sister etc.
	 * @return
	 */
	public static int getCountMultiplierCount()
	{
		Connection c;
		PreparedStatement stmt;
		ResultSet rs;
		int count = 0;
		
		c = DatabaseManagment.getConnection();
		try {
			stmt = c.prepareStatement("SELECT COUNT(DISTINCT type) FROM typemultipliers;");
			rs = stmt.executeQuery();
			count  = rs.next()? rs.getInt(1) : 0;
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}


	/**
	 * This method returns all of the classes from typemiltipliers in a String array
	 * @return
	 */
	public static String[] getMultiplierStrings() {
		Connection c;
		PreparedStatement stmt;
		ResultSet rs;
		String [] types = null;
		
		types = new String[getCountMultiplierCount()];
		c = DatabaseManagment.getConnection();
		try {
			stmt = c.prepareStatement("SELECT DISTINCT type FROM typemultipliers;");
			rs = stmt.executeQuery();
			int index = 0;
			while(rs.next())
			{
				types[index] = rs.getString(1);
				index++;
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return types;
	}


	public static int getMulitplierNum(String type) {
		Connection c;
		PreparedStatement stmt;
		ResultSet rs;
		Integer multiplier = null;
		
		c = DatabaseManagment.getConnection();
		try {
			stmt = c.prepareStatement("SELECT multiplier FROM typemultipliers WHERE type = '" + type + "';");
			rs = stmt.executeQuery();
			rs.next();
			multiplier = rs.getInt(1);
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return multiplier;
	}
}
