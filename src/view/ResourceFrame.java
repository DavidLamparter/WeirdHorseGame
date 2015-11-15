package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.ListOfWorkers;
import model.Resource;

public class ResourceFrame extends JFrame {
	//  Instance variables and stuffs
	private JPanel holder = new JPanel();
	private JPanel imageGoesHere = new JPanel();
	private JTextField description = new JTextField();
	private JTextField nameOfResource = new JTextField();
	private JButton exit = new JButton("X");
	private JButton harvest = new JButton("HARVEST!");
	private ListOfWorkers theWorkmen;
	private Resource curr;
	private Point arrayPos;
	//Image resourcePic;  cuz that would be dope
	
	public ResourceFrame(Point mousePos, Point arrayPos, Resource resource, ListOfWorkers theWorkmen) {
		//  sets the size of the frame and location 10 pixels away from your mouse
		this.setSize(200, 100);
		this.setLocation(mousePos.x+10, mousePos.y-getHeight()/3);
		/*
		 * Need to add if statements to see if this is off the screen cuz that would not be dope
		 */
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.theWorkmen = theWorkmen;
		this.arrayPos = arrayPos;
		curr = resource;
		//  sets up all the Java Swing
		imageGoesHere.setLayout(new BorderLayout());
		//  what if we added the image from what was clicked
		holder.setSize(this.getSize());
		holder.setLayout(null);
		
		//  The top section with the name and exit button
		nameOfResource.setSize(getWidth()-25, 25);
		nameOfResource.setLocation(0,0);
		nameOfResource.setText(curr.getName());
		exit.setSize(25,25);
		exit.setLocation(nameOfResource.getWidth(), 0);
		
		//  Sets up the image location
		imageGoesHere.setSize(getWidth()/2, getHeight()-25);
		imageGoesHere.setBackground(Color.BLUE);
		imageGoesHere.setLocation(0, 25);
		
		//  sets up a location for the description of the resource
		description.setLocation(getWidth()/2, 25);
		description.setSize(imageGoesHere.getWidth(),imageGoesHere.getHeight()-25);
		
		//  the harvest button!
		harvest.setLocation(imageGoesHere.getWidth(), description.getHeight()+25);
		harvest.setSize(imageGoesHere.getWidth(), 25);
		
		//  adds some text
		nameOfResource.setText(resource.getName());
		description.setText("Quanity: " + resource.getQuantity());
		
		//  our action listeners
		harvest.addActionListener(new HarvestListener());
		exit.addActionListener(new ExitListener());
		
		//  adds everything to holder for what purpose? yes.
		holder.add(nameOfResource);
		holder.add(exit);
		holder.add(imageGoesHere);
		holder.add(description);
		holder.add(harvest);
		
		this.add(holder);
		this.setVisible(true);
	}
	
	private class HarvestListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			theWorkmen.findClosest(arrayPos);  //  THIS WILL RETURN A WORKER SO .findThePath or something idk?
			//  curr.incrementNumberOfHarvesters();  we could add this so they know how many workers are working this tile
		}
	}
	private class ExitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
}
