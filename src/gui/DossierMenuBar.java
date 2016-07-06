package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;

import dataEntry.DatabaseManagment;

import javax.swing.ImageIcon;
 
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JFrame;

public class DossierMenuBar {
	
	JTextArea output;
    JScrollPane scrollPane;
    JMenuBar menuBar;
    
	public JMenuBar createMenuBar() {
		menuBar = new JMenuBar();
		// Create the menu bar.
		menuBar.add(createFileMenu());
		menuBar.add(createDefaultMenu());
		menuBar.add(createNavigation());
		return menuBar;
	}
	
	private JMenu createDefaultMenu(){
		JMenu menu, submenu;
		JMenuItem menuItem;
		JRadioButtonMenuItem rbMenuItem;
		JCheckBoxMenuItem cbMenuItem;
		// Build the first menu.
		menu = new JMenu("A Menu");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");

		// a group of JMenuItems
		menuItem = new JMenuItem("A text-only menu item", KeyEvent.VK_T);
		// menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menu.add(menuItem);

		ImageIcon icon = createImageIcon("images/middle.gif");
		menuItem = new JMenuItem("Both text and icon", icon);
		menuItem.setMnemonic(KeyEvent.VK_B);
		menu.add(menuItem);

		menuItem = new JMenuItem(icon);
		menuItem.setMnemonic(KeyEvent.VK_D);
		menu.add(menuItem);

		// a group of radio button menu items
		menu.addSeparator();
		ButtonGroup group = new ButtonGroup();

		rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
		rbMenuItem.setSelected(true);
		rbMenuItem.setMnemonic(KeyEvent.VK_R);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Another one");
		rbMenuItem.setMnemonic(KeyEvent.VK_O);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		// a group of check box menu items
		menu.addSeparator();
		cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
		cbMenuItem.setMnemonic(KeyEvent.VK_C);
		menu.add(cbMenuItem);

		cbMenuItem = new JCheckBoxMenuItem("Another one");
		cbMenuItem.setMnemonic(KeyEvent.VK_H);
		menu.add(cbMenuItem);

		// a submenu
		menu.addSeparator();
		submenu = new JMenu("A submenu");
		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JMenuItem("An item in the submenu");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		submenu.add(menuItem);

		menuItem = new JMenuItem("Another item");
		submenu.add(menuItem);
		menu.add(submenu);
		return menu;
	}
	
	private JMenu createNavigation()
	{
		JMenu menu;
		JMenuItem menuItem;
		
		// Build second menu in the menu bar.
		menu = new JMenu("Navigation");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
		menuBar.add(menu);
		
		menuItem = new JMenuItem("Target Selection");
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingWindowChanger.openTargetSelectionWindow();
			}
			
		});
		menu.add(menuItem);
		menuItem = new JMenuItem("Edit Score Multipliers");
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingWindowChanger.openMultiplierEditWindow();
			}
			
		});
		menu.add(menuItem);
		return menu;
	}
	
	private JMenu createFileMenu()
	{
		JMenu fileMenu, submenu;
		JMenuItem menuItem;
		
		fileMenu = new JMenu("File");
		submenu = new JMenu("New");
		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JMenuItem("Contact");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Contact Pressed");
				//SwingWindowChanger.openTargetSelectionWindow();
			}
		});
		submenu.add(menuItem);
		
		menuItem = new JMenuItem("Event");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Event Pressed");
				//SwingWindowChanger.openTargetSelectionWindow();
			}
			
		});
		submenu.add(menuItem);
		fileMenu.add(submenu);
		
		//IMPORT SUBMENU
		submenu = new JMenu("Import");
		submenu.setMnemonic(KeyEvent.VK_S);
		menuItem = new JMenuItem("Facebook Data");
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Import Facebook Data");
				facebook.FBParseDriver.fbParser();
			}
		});
		submenu.add(menuItem);
		fileMenu.add(submenu);
		
		//RESET MENU
		fileMenu.addSeparator();
		menuItem = new JMenuItem("Reset Database");
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Delete Pressed");
				DatabaseManagment.deleteAll();
			}
		});
		fileMenu.add(menuItem);
		return fileMenu;
		
	}
	
	public Container createContentPane() {
		// Create the content-pane-to-be.
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setOpaque(true);

		// Create a scrolled text area.
		output = new JTextArea(5, 30);
		output.setEditable(false);
		scrollPane = new JScrollPane(output);

		// Add the text area to the content pane.
		contentPane.add(scrollPane, BorderLayout.CENTER);

		return contentPane;
	}


	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = MenuBar.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("MenuLookDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		DossierMenuBar menu = new DossierMenuBar();
		frame.setJMenuBar(menu.createMenuBar());
		frame.setContentPane(menu.createContentPane());

		// Display the window.
		frame.setSize(450, 260);
		frame.setVisible(true);
		DossierGuiFrame.setNewWindowLocation();
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}