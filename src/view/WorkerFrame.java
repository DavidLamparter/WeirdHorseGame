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
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;

import model.Game;
import model.Job;
import model.ListOfWorkers;
import model.Resource;
import model.ShortestPathCalculator;
import model.Worker;


public class WorkerFrame extends JFrame implements Observer {
	//  Instance variables and stuffs
	private JPanel holder = new JPanel();
	private JTextArea description = new JTextArea();
	private JTextField nameOfResource = new JTextField();
	private JButton exit = new JButton("X");
	private JButton harvest = new JButton("HARVEST!");
	private Font titleFont = new Font("Arial", Font.BOLD, 20);
	private Font descriptionFont = new Font("Arial", Font.PLAIN, 14);
	private PicPanel imageGoesHere;
	private Worker workmen;
	private Point arrayPos;
	
	//Image resourcePic;  cuz that would be dope
	
	public WorkerFrame(Point mousePos, Point arrayPos, Worker workmen) {
		this.setSize(200, 200);
		this.setLocation(mousePos.x+10, mousePos.y-getHeight()/3);
		/*
		 * Need to add if statements to see if this is off the screen cuz that would not be dope
		 */
		this.workmen = workmen;
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.arrayPos = arrayPos;
		
		//  sets up all the Java Swing
		//imageGoesHere.setLayout(new BorderLayout());
		//  what if we added the image from what was clicked
		holder.setSize(this.getSize());
		holder.setLayout(null);
		
		//  The top section with the name and exit button
		nameOfResource.setSize(getWidth()-25, 25);
		nameOfResource.setLocation(0,0);
		//  nameOfResource.setText(workmen.getName());
		nameOfResource.setHorizontalAlignment(SwingConstants.CENTER);
		nameOfResource.setFont(titleFont);
		exit.setSize(25,25);
		exit.setLocation(nameOfResource.getWidth(), 0);
		
		//  Sets up the image location
		imageGoesHere = new PicPanel(workmen.animationFrameFileName());
		imageGoesHere.setSize(getWidth()/2, getHeight()-25);
		imageGoesHere.setBackground(Color.WHITE);
		imageGoesHere.setLocation(0, 25);
		
		//  sets up a location for the description of the resource
		description.setLocation(getWidth()/2, 25);
		description.setSize(imageGoesHere.getWidth(),imageGoesHere.getHeight()-25);
		description.setFont(descriptionFont);
		
		//  the harvest button!
		harvest.setLocation(imageGoesHere.getWidth(), description.getHeight()+25);
		harvest.setSize(imageGoesHere.getWidth(), 25);
		
		//  adds some text
		//  nameOfResource.setText(resource.getName());
		if(workmen.isBusy()) {
			description.setText("They are busy");
		}
		else {
			description.setText("They are idle");
		}
		
		double tempDistribution = .01;
		//  0 to 4 times
		//  running times
		int timesToRun = (int)(Math.random()*3);
		for(int i = 0; i < timesToRun; i++) {
			tempDistribution-=.001;
		}
			description.setText(description.getText() + "\nHunger: "
					+ workmen.getHunger() + "\nFatigue: "
					+ workmen.getFatigue() + "\nTemp: "
					+ round((10-(workmen.getColdness()*.041))*(9.852+tempDistribution)) 
					+ "\nPref: " + workmen.getPreference());
					//  this is a formula that will take the normal body temp 98.6 and slowly drop it
					//  till it gets below 94.5 I don't think this should change unless it's winter
			
		//  our action listeners
		harvest.addActionListener(new HarvestListener());
		exit.addActionListener(new ExitListener());
		
		//  adds everything to holder for what purpose? yes.
		holder.setBackground(Color.WHITE);
		holder.add(nameOfResource);
		holder.add(exit);
		holder.add(imageGoesHere);
		holder.add(description);
		holder.add(harvest);
		
		this.add(holder);
		this.setVisible(true);
	}
	//  Rounds to 2 decimal places
	private double round(double toRound) {
		toRound *= 100;
		toRound = (int)toRound;
		toRound /= 100;
		return toRound;
	}
	
	private class HarvestListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//  game.addJob(new Job(arrayPos, curr));
			/*Worker theJobDoer = theWorkmen.findClosest(arrayPos);
			ShortestPathCalculator calc = new ShortestPathCalculator(game.getMap());
			theJobDoer.toLocation(calc.getShortestPath(theJobDoer.getPoint(), arrayPos));
			System.out.println(calc.getShortestPath(theJobDoer.getPoint(), arrayPos)); */
			dispose(); 
			//  curr.incrementNumberOfHarvesters();  we could add this so they know how many workers are working this tile
		}
	}
	private class ExitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	@Override
	public void update(Observable o, Object arg) {
		double tempDistribution = .01;
		//  0 to 4 times
		//  running times
		int timesToRun = (int)(Math.random()*3);
		for(int i = 0; i < timesToRun; i++) {
			tempDistribution-=.001;
		}
		if(workmen.isBusy()) {
			description.setText("They are busy");
		}
		else {
			description.setText("They are idle");
		}
		description.setText(description.getText() + "\nHunger: "
				+ workmen.getHunger() + "\nFatigue: "
				+ workmen.getFatigue() + "\nTemp: "
				+ round((10-(workmen.getColdness()*.041))*(9.852+tempDistribution)) 
				+ "\nPref: " + workmen.getPreference()
				+ "\nTask: " + workmen.getJob().x + ","
				+ workmen.getJob().y);
		
	}
	public void moveRight() {
		this.setLocation(this.getLocation().x+MapPanel.MAP_TILE_WIDTH, this.getLocation().y);
	}
	public void moveLeft() {
		this.setLocation(this.getLocation().x-MapPanel.MAP_TILE_WIDTH, this.getLocation().y);
	}
	public void moveUp() {
		this.setLocation(this.getLocation().x, this.getLocation().y+MapPanel.MAP_TILE_HEIGHT);
	}
	public void moveDown() {
		this.setLocation(this.getLocation().x, this.getLocation().y-MapPanel.MAP_TILE_HEIGHT);
	}
}
