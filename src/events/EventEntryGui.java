package events;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class EventEntryGui extends JPanel implements Runnable {

	static JFrame frame;

	@Override
	public void run() {
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		createAndShowGUI();
	}

	public EventEntryGui() {
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
				extractEventInfo();
				DossierGuiFrame.setNewWindowLocation();
				frame.dispose();
				createAndShowGUI();
			}
		});
		this.add(saveButton);
	}

	private void extractEventInfo() {
		JPanel panel;
		JLabel label;
		JTextField text;
		String value ="";
		Component c;
		JDatePickerImpl datePicker;
		int eID;

		eID = EventCreation.createNewEvent();
		for (int i = 0; i < this.getComponentCount() - 1; i++) {
			panel = (JPanel) this.getComponent(i);
			label = (JLabel) panel.getComponent(0);
			System.out.print(label.getText());
			c = panel.getComponent(1);
			if (c instanceof JTextField) {
				text = (JTextField) c;
				System.out.print(", " + text.getText());
				value = text.getText();
			} else if (c instanceof JDatePickerImpl) {
				datePicker = (JDatePickerImpl) c;
				System.out.println(datePicker.getJFormattedTextField().getText());
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
	private static void createAndShowGUI() {
		// Create and set up the window.
		DossierGuiFrame.createNamedFrame("New Event");
		frame = DossierGuiFrame.getFrame();

		// Add content to the window.
		frame.add(new EventEntryGui());

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		createAndShowGUI();
	}
}
