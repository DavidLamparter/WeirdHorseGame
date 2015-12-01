/*================================================================================================
|   Assignment:  FINAL PROJECT - Settlement Management
|      Authors:  David Lamparter (Lamparter@email.arizona.edu)
|                Kyle Grady (kgrady1@email.arizona.edu)
|    			 Kyle DeTar (kdeTar@email.arizona.edu)
|	  			 Brett Cohen (brett7@email.arizona.edu)
|                       
|       Course:  335
|   Instructor:  R. Mercer
|           PM:  Sean Stephens
|     Due Date:  12/9/15
|
|  Description:  This program . . .
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;

import model.Buildable;
import model.Game;
import model.Job;
import model.ListOfWorkers;
import model.Resource;
import model.ShortestPathCalculator;
import model.Storage;
import model.Worker;


public class BuildingFrame extends JFrame {
	//  Instance variables and stuffs
	private JPanel holder = new JPanel();
	private JTextArea description = new JTextArea();
	private JTextField nameofBuilding = new JTextField();
	private JButton exit = new JButton("X");
	private Font titleFont = new Font("Arial", Font.BOLD, 20);
	private Font descriptionFont = new Font("Arial", Font.PLAIN, 14);
	private PicPanel imageGoesHere;
	private Storage theBuilding;
	private int resourceQuantity;
	private Storage curr;
	private String buildingName;
	private Point arrayPos;
	private Game game;
	
	//Image resourcePic;  cuz that would be dope
	
	public BuildingFrame(Point mousePos, Point arrayPos, Storage building, Game game) {
		//  sets the size of the frame and location 10 pixels away from your mouse
		this.game = game;
		theBuilding = building;
		resourceQuantity = theBuilding.getQuantity();
		buildingName = theBuilding.getName();
		this.setSize(200, 125);
		this.setLocation(mousePos.x+10, mousePos.y-getHeight()/3);
		/*
		 * Need to add if statements to see if this is off the screen cuz that would not be dope
		 */
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.arrayPos = arrayPos;
		//  sets up all the Java Swing
		//imageGoesHere.setLayout(new BorderLayout());
		//  what if we added the image from what was clicked
		holder.setSize(this.getSize());
		holder.setLayout(null);
		
		//  The top section with the name and exit button
		nameofBuilding.setSize(getWidth()-25, 25);
		nameofBuilding.setLocation(0,0);
		nameofBuilding.setText(theBuilding.getName());
		nameofBuilding.setHorizontalAlignment(SwingConstants.CENTER);
		nameofBuilding.setFont(titleFont);
		exit.setSize(25,25);
		exit.setLocation(nameofBuilding.getWidth(), 0);
		
		//  Sets up the image location
		imageGoesHere = new PicPanel(theBuilding.getImageFile());
		imageGoesHere.setSize(getWidth()/2, getHeight()-25);
		//imageGoesHere.setBackground(Color.WHITE);
		imageGoesHere.setLocation(0, 25);
		
		//  sets up a location for the description of the resource
		description.setLocation(getWidth()/2, 25);
		description.setSize(imageGoesHere.getWidth(),imageGoesHere.getHeight()-25);
		description.setFont(descriptionFont);
		
		
		//  adds some text
		nameofBuilding.setText(curr.getName());
		description.setText("\nQuanity: " + theBuilding.getQuantity() );
		
		//  our action listeners
		exit.addActionListener(new ExitListener());
		
		//  adds everything to holder for what purpose? yes.
		holder.setBackground(Color.WHITE);
		holder.add(nameofBuilding);
		holder.add(exit);
		holder.add(imageGoesHere);
		holder.add(description);
		
		this.add(holder);
		this.setVisible(true);
	}
	

	private class ExitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
}

