package scores;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import dataEntry.DatabaseAccess;
import dataEntry.DatabaseInfoUpdate;
import gui.DossierGuiFrame;
import gui.DossierMenuBar;

public class EditMultipliersWindow extends JPanel implements Runnable {
	static JFrame frame;

	@Override
	public void run() {
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		createAndShowGUI();
	}

	public EditMultipliersWindow() {
		new EditMultipliersWindow(DatabaseAccess.getCountMultiplierCount());
	}

	private EditMultipliersWindow(int typeCount) {
		super(new GridLayout(typeCount - 1, 1));

		JPanel typePanel;
		JLabel textType;
		JButton saveButton, deleteButton, newTypeButton;

		String[] typesWithDefaults = DatabaseAccess.getMultiplierStrings();
		ArrayList<String> types = new ArrayList<String>();
		// Get rid of Default and Owner
		for (String s : typesWithDefaults) {
			if (s.equals("DEFAULT"))
				continue;
			if (s.equals("OWNER"))
				continue;
			types.add(s);
		}

		for (String type : types) {
			typePanel = new JPanel();
			typePanel.setLayout(new GridLayout(1, 4));
			textType = new JLabel(type);
			typePanel.add(textType);
			int multiplier = DatabaseAccess.getMulitplierNum(type);
			SpinnerModel multValue = new SpinnerNumberModel(multiplier, // initial
					multiplier - 99999999, // min
					multiplier + 99999999, // max
					1); // step
			JSpinner spinner = new JSpinner(multValue);

			typePanel.add(spinner);
			saveButton = new JButton("Save Changes");
			saveButton.setName(type);
			deleteButton = new JButton("Delete");
			deleteButton.setName(type);

			saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton current = (JButton) e.getSource();
					JPanel sourcePanel = (JPanel) current.getParent();
					JSpinner num = (JSpinner) sourcePanel.getComponent(1);
					DatabaseInfoUpdate.updateMultiplier(current.getName(), (Integer) num.getValue());
					frame.dispose();
					createAndShowGUI();
				}
			});

			deleteButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton current = (JButton) e.getSource();
					JPanel sourcePanel = (JPanel) current.getParent();
					DatabaseInfoUpdate.deleteMultiplier(current.getName());
					System.out.println("Deleted " + current.getName());
					Container c = sourcePanel.getParent();
					c.remove(sourcePanel);
					c.repaint();
				}
			});

			typePanel.add(saveButton);
			typePanel.add(deleteButton);
			this.add(typePanel);
		}
		JPanel newMultiplierPanel = new JPanel();
		newMultiplierPanel.add(new JLabel("Enter new types here -->"));
		newMultiplierPanel.setLayout(new GridLayout(1, 4));
		SpinnerModel multValue = new SpinnerNumberModel(1, // initial
				-99999999, // min
				99999999, // max
				1); // step
		JSpinner spinner = new JSpinner(multValue);
		JTextField name = new JTextField();
		name.setToolTipText("Enter The name of the class or type here");
		newMultiplierPanel.add(name);
		newMultiplierPanel.add(spinner);
		JButton submitNewTypeButton = new JButton("Add New Type");
		submitNewTypeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JPanel sourcePanel = (JPanel)((JButton) e.getSource()).getParent();
				String name = ((JTextField) sourcePanel.getComponent(1)).getText();
				int num = (Integer)((JSpinner) sourcePanel.getComponent(2)).getValue();
				
				DatabaseInfoUpdate.addNewMultiplier(name, num);
				System.out.println("Adding " + name + " : " + num);
				frame.dispose();
				createAndShowGUI();
			}

		});
		newMultiplierPanel.add(submitNewTypeButton);
		this.add(newMultiplierPanel);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		DossierGuiFrame.createNamedFrame("Edit Score Multipliers");
		frame = DossierGuiFrame.getFrame();
		
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
