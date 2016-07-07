package gui;

import contacts.TargetSelectionAutoComplete;
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
	
	public static void openNewEventWindow() {
		try {
			DossierGuiFrame.setNewWindowLocation();
			DossierGuiFrame.getFrame().dispose();
			EventEntryGui.createNewEventGUI();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void openOldEventEditorWindow(int eID) {
		try {
			DossierGuiFrame.setNewWindowLocation();
			DossierGuiFrame.getFrame().dispose();
			EventEntryGui.editOldEventGUI(eID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
