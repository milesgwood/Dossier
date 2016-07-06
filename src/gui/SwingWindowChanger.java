package gui;

import contactEntries.TargetSelectionAutoComplete;
import events.EventEntryGui;
import scores.EditMultipliersWindow;

public class SwingWindowChanger {

	public static void openTargetSelectionWindow() {
		try {
			DossierGuiFrame.setNewWindowLocation();
			DossierGuiFrame.getFrame().dispose();
			new Thread(new TargetSelectionAutoComplete()).start();
			// TargetSelectionAutoComplete.showTargetSelectionGUI();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void openMultiplierEditWindow() {
		try {
			DossierGuiFrame.setNewWindowLocation();
			DossierGuiFrame.getFrame().dispose();
			new Thread(new EditMultipliersWindow()).start();
			// TargetSelectionAutoComplete.showTargetSelectionGUI();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void openEventEntryGui() {
		try {
			DossierGuiFrame.setNewWindowLocation();
			DossierGuiFrame.getFrame().dispose();
			new Thread(new EventEntryGui()).start();
			// TargetSelectionAutoComplete.showTargetSelectionGUI();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
