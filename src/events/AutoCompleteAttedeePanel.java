package events;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

import contacts.AutocompleteJComboBox;
import contacts.StringSearchable;
import dataEntry.ContactIDNotFoundException;
import dataEntry.DatabaseAccess;
import gui.DossierGuiFrame;

public class AutoCompleteAttedeePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	public void run() {
		new AutoCompleteAttedeePanel();
	}

	public AutoCompleteAttedeePanel() {

		List<String> myWords = new ArrayList<String>();
		myWords.addAll(dataEntry.DatabaseSearching.getAllNames());
		StringSearchable searchable = new StringSearchable(myWords);
		final AutocompleteJComboBox combo = new AutocompleteJComboBox(searchable);

		JButton select = new JButton("Select");
		JButton reset = new JButton("Reset");
		
		select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectionButtonPressed();
			}

			/**
			 * When the selection button is pressed, the ID of the name is
			 * retrieved and the InfoDisplay window is opened.
			 */
			private void selectionButtonPressed() {
				int id;
				String name = (String) combo.getSelectedItem();
				try {
					id = DatabaseAccess.getIdFromFullName(name);
					DossierGuiFrame.setNewWindowLocation();
					new Attendee(name, id);

				} catch (ContactIDNotFoundException e) {
					// CREATE A NEW CONTACT ON THE INPUT CONTACT PAGE
					e.printStackTrace();
				}
			}
		});

		/**
		 * The reset button empties the text box and restarts the program.
		 */
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetButtonPressed();
			}

			private void resetButtonPressed() {
				System.out.println("Pressed the Reset Button");
				restart();
			}
		});

		this.setLayout(new FlowLayout());
		this.add(combo);
		this.add(select);
		this.add(reset);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	/**
	 * This method restarts the selection screen when Swing freezes. An
	 * understanding of threading is needed to fix this freezing problem.
	 */
	public static void restart() {
		try {
			Runtime.getRuntime().exec("java -jar Dossier.jar");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Didn't find the jar file");
			e.printStackTrace();
		}
		System.exit(0);
	}
}
