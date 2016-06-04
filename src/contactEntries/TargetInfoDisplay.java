package contactEntries;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import dataEntry.DatabaseAccess;
import dataEntry.DatabaseInfoUpdate;

public class TargetInfoDisplay {

	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    static void showTargetInfoGUI(final int id) {
        //Create and set up the window.
        final JFrame frame = new JFrame("Dossier");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        final ArrayList<JPanel> textSectionsList = new ArrayList<JPanel>();
        final ArrayList<JPanel> buttonSectionsList = new ArrayList<JPanel>();
        		
        JPanel first = new JPanel(new GridLayout(1, 2));
        first.add(new JLabel("First"));
        first.add(new JTextField(""));
        textSectionsList.add(first);
        
        JPanel middle = new JPanel(new GridLayout(1, 2));
        middle.add(new JLabel("Middle"));
        middle.add(new JTextField(""));
        textSectionsList.add(middle);
        
        JPanel last = new JPanel(new GridLayout(1, 2));
        last.add(new JLabel("Last"));
        last.add(new JTextField(""));
        textSectionsList.add(last);
        //--------------------------------
        
        JPanel alias = new JPanel(new GridLayout(1, 2));
        alias.add(new JLabel("Alias"));
        alias.add(new JTextField(""));
        textSectionsList.add(alias);
        
        JPanel age = new JPanel(new GridLayout(1, 2));
        age.add(new JLabel("Age"));
        age.add(new JTextField(""));
        textSectionsList.add(age);
        
        JPanel address = new JPanel(new GridLayout(1, 2));
        address.add(new JLabel("Address"));
        address.add(new JTextField(""));
        textSectionsList.add(address);
        
        JPanel city = new JPanel(new GridLayout(1, 2));
        city.add(new JLabel("City"));
        city.add(new JTextField(""));
        textSectionsList.add(city);
        
        JPanel State = new JPanel(new GridLayout(1, 2));
        State.add(new JLabel("State"));
        State.add(new JTextField(""));
        textSectionsList.add(State);
        
        JPanel phone = new JPanel(new GridLayout(1, 2));
        phone.add(new JLabel("Phone"));
        phone.add(new JTextField(""));
        textSectionsList.add(phone);
        
        JPanel email = new JPanel(new GridLayout(1, 2));
        email.add(new JLabel("Email"));
        email.add(new JTextField(""));
        textSectionsList.add(email);
        
        JPanel interests = new JPanel(new GridLayout(1, 2));
        interests.add(new JLabel("Interests"));
        interests.add(new JTextField(""));
        textSectionsList.add(interests);
        
        JPanel sexPanel = new JPanel(new GridLayout(1, 3));
        sexPanel.add(new JLabel("Sex"));
        JRadioButton male = new JRadioButton("Male");
        JRadioButton female = new JRadioButton("Female");
        male.setText("M");
        female.setText("F");
        ButtonGroup sexGroup = new ButtonGroup();
        sexGroup.add(male);
        sexGroup.add(female);
        sexPanel.add(male);
        sexPanel.add(female);
        buttonSectionsList.add(sexPanel);
        
        final JPanel allInfo = new JPanel(new GridLayout(15, 1));
        
        JLabel wholeName =  new JLabel("New Contact");
        allInfo.add(wholeName);
        
        for(JPanel panel: textSectionsList)
        {
        	allInfo.add(panel);
        }
        for(JPanel panel: buttonSectionsList)
        {
        	allInfo.add(panel);
        }
        
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submitButtonPressed();
			}

			/**
			 * When the selection button is pressed, the ID of the name
			 * is retrieved and the InfoDisplay window is opened.
			 */
			private void submitButtonPressed() {
				System.out.println("Pressed the Submit Button");
				
				JLabel label;
				JTextField text;
				JRadioButton button;
				String attribute, value;
				for(JPanel panel: textSectionsList)
		        {
		        	label = (JLabel)panel.getComponent(0);
		        	text = (JTextField)panel.getComponent(1);
		        	attribute = label.getText();
		        	value = text.getText();
		        	if(text.getText().length() > 0)
		        	{
		        		DatabaseInfoUpdate.updateAttributeWithString(attribute , value, id);
		        	}
		        	System.out.println("The label is :" + attribute);
		        	System.out.println("The text is :" + value);
		        }
				for(JPanel panel: buttonSectionsList)
		        {
					label = (JLabel)panel.getComponent(0);
					value = "null";
					for(int i = 1 ; i < panel.getComponentCount() ; i++)
					{
						button = (JRadioButton)panel.getComponent(i);
						if(button.isSelected())
						{
							value = button.getText();
							break;
						}
					}
		        	attribute = label.getText();
		        	if(value != null)
		        	{
		        		DatabaseInfoUpdate.updateAttributeWithString(attribute , value, id);
		        	}
		        	System.out.println("The label is :" + attribute);
		        	System.out.println("The text is :" + value);
		        }
			}
		});
        
        JButton back = new JButton("Back to Selection");
        back.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		backButtonPressed();
        	}
        	
        	private void backButtonPressed(){
        		try {
        			new Thread(new TargetSelectionAutoComplete()).start();
					//TargetSelectionAutoComplete.showTargetSelectionGUI();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		frame.dispose();
        	}});
        
        allInfo.add(submit);
        allInfo.add(back);
         
        //Here is we add the info if we are editing
        
        String result;
        if(id > -1)
        {
        	wholeName.setText(DatabaseAccess.getFullName(id));
        	for(JPanel panel: textSectionsList)
            {
            	JLabel label = (JLabel)panel.getComponent(0);
            	result = DatabaseAccess.getOnePieceOfInfo(label.getText(), id);
            	JTextField text = (JTextField)panel.getComponent(1);
            	text.setText(result);
            }
        	for(JPanel panel: buttonSectionsList)
            {
            	JLabel label = (JLabel)panel.getComponent(0);
            	result = DatabaseAccess.getOnePieceOfInfo(label.getText(), id);
            	if(result.equalsIgnoreCase("M"))
            	{
            		male.setSelected(true);
            	}
            	else if(result.equalsIgnoreCase("F"))
            	{
            		female.setSelected(true);
            	}
            	else
            	{
            		male.setSelected(false);
            		female.setSelected(false);
            	}
            }
        }
        else
        {
        	//CREATE A NEW ID FOR THIS CONTACT
        }
        Container c = frame.getContentPane();
        c.add(allInfo);
        //Display the window.
        Dimension minimum = new Dimension(500, 60);
		frame.setMinimumSize(minimum);
        frame.pack();
        frame.setVisible(true);
    }

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				showTargetInfoGUI(0);
			}
		});
	}
}
