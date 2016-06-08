package scores;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import dataEntry.DatabaseAccess;
import dataEntry.DatabaseInfoUpdate;

public class EditMultipliersWindow extends JPanel implements Runnable {

	@Override
	public void run() {
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		createAndShowGUI();
	}

	public EditMultipliersWindow() {
		new EditMultipliersWindow(DatabaseAccess.getCountMultiplierCount());
	}

	public EditMultipliersWindow(int typeCount) {
		super(new GridLayout(typeCount - 1, 1));

		JPanel typePanel;
		JTextField textType;
		JButton saveButton, deleteButton, newTypeButton;

		String[] typesWithDefaults = DatabaseAccess.getMultiplierStrings();
		ArrayList<String> types = new ArrayList<String>();
		//Get rid of Default and Owner
		for(String s : typesWithDefaults)
		{
			if (s.equals("DEFAULT"))
				continue;
			if (s.equals("OWNER"))
				continue;
			types.add(s);
		}

		for (String type : types) {
			typePanel = new JPanel();
			typePanel.setLayout(new GridLayout(1, 4));
			textType = new JTextField(type);
			typePanel.add(textType);
			int multiplier = DatabaseAccess.getMulitplierNum(type);
			SpinnerModel multValue = new SpinnerNumberModel(multiplier, // initial
					multiplier - 100, // min
					multiplier + 100, // max
					1); // step
			JSpinner spinner = new JSpinner(multValue);

			typePanel.add(spinner);
			saveButton = new JButton("Save Change");
			deleteButton = new JButton("Delete");

			saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton current = (JButton) e.getSource();
					JPanel sourcePanel = (JPanel) current.getParent();
					System.out.println(current);
					System.out.println(sourcePanel);
					// DatabaseInfoUpdate.updateMultipliers();
				}
			});

			typePanel.add(saveButton);
			typePanel.add(deleteButton);
			this.add(typePanel);
		}
		this.add(new JButton("Add New Type"));
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Edit Score Multipliers");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window.
		frame.add(new EditMultipliersWindow(DatabaseAccess.getCountMultiplierCount()));

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		createAndShowGUI();
	}
}
