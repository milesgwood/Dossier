package events;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gui.DossierGuiFrame;

public class SelectedAttendees {
	private static ArrayList<Attendee> attendees = new ArrayList<Attendee>();
	private JPanel namePanel;
	
	public static void main(String[] args){
		new SelectedAttendees();
	}
	
	public static void addAttendee(Attendee attendee)
	{
		attendees.add(attendee);
		createNamePanel();
		JFrame frame = DossierGuiFrame.getFrame();
		frame.dispose();
		new SelectedAttendees();
	}
	
	public SelectedAttendees()
	{
		DossierGuiFrame.createNamedFrame("Select Event Attendees");
		JFrame frame = DossierGuiFrame.getFrame();
		
		namePanel = createNamePanel();
		frame.setLayout(new GridLayout(2, 1));
		frame.add(new AutoCompleteAttedeePanel());
		frame.add(namePanel);
		Dimension minimum = new Dimension(500, 60);
		frame.setMinimumSize(minimum);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		DossierGuiFrame.setFrame(frame);
	}

	private static JPanel createNamePanel() {
		JPanel names = new JPanel();
		names.setLayout(new GridLayout(attendees.size(), 1));
		for(JPanel panel: attendees)
		{
			names.add(panel);
		}
		return names;
	}
}
