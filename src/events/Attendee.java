package events;

import java.awt.FlowLayout;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dataEntry.DatabaseManagment;

public class Attendee extends JPanel{

	private static final long serialVersionUID = 1L;
	private static int eID;
	private JLabel name = new JLabel("DEFAULT");
	private JButton deleteButton = new JButton("Delete");
	
	Attendee(String person, Integer pID)
	{
		super(new FlowLayout());
		name.setText(person);
		name.setName(pID.toString());
		deleteButton.setName(pID.toString());
		this.add(name);
		this.add(deleteButton);
		addPersonToEvent(pID);
		SelectedAttendees.addAttendee(this);
	}

	public static void setEventID(int id) {
		eID = id;
	}
	
	public static void addPersonToEvent(int pID)
	{
		Connection c;
		PreparedStatement stmt;
		
		try {
			c = DatabaseManagment.getConnection();
			String sql = "INSERT INTO people_at_events(eID, pID) VALUES("+eID+",?);";
			stmt = c.prepareStatement(sql);
			stmt.setInt(1, pID);
			stmt.execute();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}