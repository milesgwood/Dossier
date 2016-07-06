package gui;

import javax.swing.JFrame;
import javax.swing.JFileChooser;

public class FileChooser extends JFrame  {
	private static String filename;
	private static String dir;

	public static void main(String[] args) {
		getFileLocation();
	}
	
	public static String getFileLocation()
	{
		new FileChooser();
		System.out.println(dir + "/" + filename);
		return dir + "/" + filename;
	}

	public FileChooser() {
		JFileChooser c = new JFileChooser();
		// Demonstrate "Open" dialog:
		int rVal = c.showOpenDialog(FileChooser.this);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			filename = c.getSelectedFile().getName();
			dir = c.getCurrentDirectory().toString();
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
		}
	}
}
