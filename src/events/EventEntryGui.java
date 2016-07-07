package events;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.jdatepicker.impl.*;

import gui.DossierGuiFrame;

@SuppressWarnings("serial")
public class EventEntryGui extends JPanel {

	static JFrame frame;

	/**
	 * If the eID is less than 1 or doesn't exist than we make a new eID
	 * 
	 * @param eID
	 */
	public EventEntryGui(final int eID) {
		super(new GridLayout(8, 1));

		JDatePickerImpl datePicker; // --THE COMPONENT WE USE FOR DATES
		JPanel eventPanel;
		JLabel attribute;
		JTextField value;
		JButton saveButton;
		String[] textFieldsNoDefault = { "Address", "City", "State", "Country" };

		// TITLE
		eventPanel = new JPanel();
		eventPanel.setLayout(new GridLayout(1, 2));
		attribute = new JLabel("Title");
		value = new JTextField();
		eventPanel.add(attribute);
		eventPanel.add(value);
		this.add(eventPanel);

		// DATE
		eventPanel = new JPanel();
		eventPanel.setLayout(new GridLayout(1, 2));
		attribute = new JLabel("Date");
		eventPanel.add(attribute);
		// DatePicker Starts Here
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		// Don't know about the formatter, but there it is...
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		eventPanel.add(datePicker);
		this.add(eventPanel);

		// Address
		for (String place : textFieldsNoDefault) {
			eventPanel = new JPanel();
			eventPanel.setLayout(new GridLayout(1, 2));
			attribute = new JLabel(place);
			value = new JTextField();
			if (place.equalsIgnoreCase("city")) {
				value.setText("Charlottesville");
			}
			;
			if (place.equalsIgnoreCase("state")) {
				value.setText("VA");
			}
			;
			if (place.equalsIgnoreCase("country")) {
				value.setText("USA");
			}
			;
			eventPanel.add(attribute);
			eventPanel.add(value);
			this.add(eventPanel);
		}

		eventPanel = new JPanel();
		eventPanel.setLayout(new GridLayout(1, 2));
		attribute = new JLabel("Thoughts");
		value = new JTextField();
		eventPanel.add(attribute);
		eventPanel.add(value);
		this.add(eventPanel);

		saveButton = new JButton("Save");
		saveButton.setText("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveEventInfo(eID);
				DossierGuiFrame.setFrame(frame);
				frame.dispose();
				new SelectedAttendees();
			}
		});
		this.add(saveButton);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 * 
	 * @throws ParseException
	 */
	public static void editOldEventGUI(int eID) {

		Component c;
		JPanel panel;
		String dateString;

		// Create and set up the window.
		DossierGuiFrame.createNamedFrame("Edit Event");
		frame = DossierGuiFrame.getFrame();

		// Add content to the window.
		ArrayList<String> data = EventCreation.getEventData(eID);
		EventEntryGui eventToEdit = new EventEntryGui(eID);
		for (int i = 0; i < eventToEdit.getComponentCount() - 1; i++) {
			c = eventToEdit.getComponent(i);
			panel = (JPanel) c;
			c = (Component) panel.getComponent(1);
			if (c instanceof JTextField) {
				((JTextField) c).setText(data.get(i + 1));
			}
			;
			if (c instanceof JDatePickerImpl) {
				dateString = data.get(i + 1);
				UtilDateModel model = new UtilDateModel();
				DateLabelFormatter df = new DateLabelFormatter();
				Date d;
				try {
					d = (Date) df.stringToValue(dateString);
					model.setValue(d);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				model.setSelected(true);
				Properties p = new Properties();
				p.put("text.today", "Today");
				p.put("text.month", "Month");
				p.put("text.year", "Year");
				JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
				JDatePickerImpl newDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
				panel.remove(1);
				panel.add(newDatePicker);
			}
		}
		frame.add(eventToEdit);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	private void saveEventInfo(int eID) {
		JPanel panel;
		JLabel label;
		JTextField text;
		String value = "";
		Component c;
		JDatePickerImpl datePicker;
		if (eID < 1) {
			eID = EventCreation.createNewEvent();
		}
		Attendee.setEventID(eID);
		for (int i = 0; i < this.getComponentCount() - 1; i++) {
			panel = (JPanel) this.getComponent(i);
			label = (JLabel) panel.getComponent(0);
			c = panel.getComponent(1);
			if (c instanceof JTextField) {
				text = (JTextField) c;
				value = text.getText();
			} else if (c instanceof JDatePickerImpl) {
				datePicker = (JDatePickerImpl) c;
				value = datePicker.getJFormattedTextField().getText();
			}
			if (value.length() > 1) {
				EventCreation.addEventData(label.getText(), value, eID);
			}
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	public static void createNewEventGUI() {
		// Create and set up the window.
		DossierGuiFrame.createNamedFrame("New Event");
		frame = DossierGuiFrame.getFrame();

		// Add empty content to the window.
		frame.add(new EventEntryGui(-1));

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
}
