package contactEntries;

import java.awt.Dimension;
import java.awt.FlowLayout;
//import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

import dataEntry.ContactIDNotFoundException;
import dataEntry.DatabaseAccess;

public class TargetSelectionAutoComplete implements Runnable{

			public void run() {

				List<String> myWords = new ArrayList<String>();
				myWords.addAll(dataEntry.DatabaseSearching.getAllNames());
				StringSearchable searchable = new StringSearchable(myWords);
				final AutocompleteJComboBox combo = new AutocompleteJComboBox(searchable);

				final JFrame frame = new JFrame("Select Target");
				JButton select = new JButton("Select");
				JButton reset = new JButton("Reset");
				select.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						selectionButtonPressed();
					}

					/**
					 * When the selection button is pressed, the ID of the name
					 * is retrieved and the InfoDisplay window is opened.
					 */
					private void selectionButtonPressed() {
						int id;
						System.out.println("Pressed the Select Button");
						String current = (String) combo.getSelectedItem();
						System.out.println(current);
						try {
							id = DatabaseAccess.getIdFromFullName(current);
							TargetInfoDisplay.showTargetInfoGUI(id);
							frame.dispose();
						} catch (ContactIDNotFoundException e) {
							// TODO Auto-generated catch block CREATE A NEW
							// CONTACT ON THE INPUT CONTACT PAGE
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

				JPanel namePanel = new JPanel(new FlowLayout());
				// JPanel namePanel = new JPanel(new GridLayout(1,3));

				namePanel.add(combo);
				namePanel.add(select);
				namePanel.add(reset);
				namePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
				frame.add(namePanel);
				Dimension minimum = new Dimension(500, 60);
				frame.setMinimumSize(minimum);
				frame.pack();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		//});
	//}

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
	
	public static void main(String[] args){
		try {
			new Thread(new TargetSelectionAutoComplete()).start();
			//showTargetSelectionGUI();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
