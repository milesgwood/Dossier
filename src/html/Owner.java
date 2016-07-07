package html;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.*;

import dataEntry.DatabaseAccess;

public class Owner {
	static String ownerAbsPath;

	public static void main(String[] args) {
	
		createOwnerHtml();
		showHtmlPage("file://" + ownerAbsPath);
	}
	
	public static void showHtmlPage(String absolutePath){
		try {
			URI uri = new URL(absolutePath).toURI();
			Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
				desktop.browse(uri);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private static void createOwnerHtml() {
		File htmlTemplateFile = new File("spy.html");
		
		String htmlString;
		String name;
		String info;
		name = DatabaseAccess.getFullName(0);
		ArrayList<ArrayList<String>> data = DatabaseAccess.getAllOwnerInfo();
		
		try {
			htmlString = FileUtils.readFileToString(htmlTemplateFile);
			htmlString = htmlString.replace("$name", name);
			
			for(int i = 0 ; i < data.get(0).size(); i++)
			{
				htmlString = htmlString.concat("<h2>" + data.get(0).get(i) + ": ");
				info = data.get(1).get(i);
				if(info.length()> 20)
				{
					htmlString = htmlString.concat("</h2><p>" + data.get(1).get(i) + "</p>");
				}
				else
				{
					htmlString = htmlString.concat(data.get(1).get(i) + "</h2>");
				}
			}
			
			File newHtmlFile = new File("Owner.html");
			ownerAbsPath = newHtmlFile.getAbsolutePath();
			FileUtils.writeStringToFile(newHtmlFile, htmlString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
