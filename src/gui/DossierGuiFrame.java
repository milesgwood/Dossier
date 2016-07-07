package gui;

import java.awt.Point;

import javax.swing.JFrame;

public class DossierGuiFrame {
	
	protected static JFrame frame = null;
	private static Point windowLocation = new Point(0,0);
	
	public static void createNamedFrame(String name)
	{
		frame = new JFrame(name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(windowLocation);

		// Create and set up the content pane.
		DossierMenuBar menu = new DossierMenuBar();
		frame.setJMenuBar(menu.createMenuBar());
		setFrame(frame);
	}
	
	public static void setFrame(JFrame f){
		frame = f;
		setNewWindowLocation();
	}
	
	public static void setNewWindowLocation()
	{
		windowLocation = frame.getLocation();
	}
	
	public static JFrame getFrame()
	{
		frame.setLocation(windowLocation);
		return frame;
	}
	
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createNamedFrame("Hello");
				JFrame frame = getFrame();
				frame.pack();
				frame.setVisible(true);
			}
		});
	}

}
