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
import model.ResourceType;
import model.ShortestPathCalculator;
import model.Worker;


public class WorkerFrame extends JFrame implements Observer {
	//  Instance variables and stuffs
	private JPanel holder = new JPanel();
	private JTextArea description = new JTextArea();
	private JTextField nameOfResource = new JTextField();
	private JButton exit = new JButton("X");
//	private JButton harvest = new JButton("DROP SHIT");
	private Font titleFont = new Font("Arial", Font.BOLD, 20);
	private Font descriptionFont = new Font("Arial", Font.PLAIN, 14);
	private PicPanel imageGoesHere;
	private Worker workmen;
	private Point arrayPos;
	private JButton speedUp = new JButton("<HTML><b><center>SHOES<br>(69 Food)</b></center></HTML>");
	private JButton harvestUp = new JButton("<HTML><b><center>GLOVES<br>(69 Stone)</b></center></HTML>");
	private JButton clothesUp = new JButton("<HTML><b><center>SCARF<br>(69 Wood)</b></center></HTML>");
	private Worker thisWorker = null;
	private Game game;
	
	//Image resourcePic;  cuz that would be dope
	
	public WorkerFrame(Point mousePos, Point arrayPos, Worker workmen, Game game) {
		this.setSize(300, 225);
		this.setLocation(mousePos.x+10, mousePos.y-getHeight()/3);
		/*
		 * Need to add if statements to see if this is off the screen cuz that would not be dope
		 */
		this.game = game;
		this.workmen = workmen;
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.arrayPos = arrayPos;
		speedUp.setFont(new Font("Arial", Font.PLAIN, 12));
		harvestUp.setFont(new Font("Arial", Font.PLAIN, 12));
		clothesUp.setFont(new Font("Arial", Font.PLAIN, 12));
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
		imageGoesHere.setSize(getWidth()/2, getHeight()-50);
		imageGoesHere.setBackground(Color.WHITE);
		imageGoesHere.setLocation(0, 25);
		
		//  sets up a location for the description of the resource
		description.setLocation(getWidth()/2, 25);
		description.setSize(imageGoesHere.getWidth(),imageGoesHere.getHeight()-25);
		description.setFont(descriptionFont);
		
		//  the speed button!
		speedUp.setLocation(0, description.getHeight()+25);
		speedUp.setSize(this.getWidth()/3, 50);
		
		//  the harvest button!
		harvestUp.setLocation(100, description.getHeight()+25);
		harvestUp.setSize(this.getWidth()/3, 50);
			
		//  the clothes button!
		clothesUp.setLocation(200, description.getHeight()+25);
		clothesUp.setSize(this.getWidth()/3, 50);
		
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
		speedUp.addActionListener(new UpgradeListener());
		harvestUp.addActionListener(new UpgradeListener());
		clothesUp.addActionListener(new UpgradeListener());
		exit.addActionListener(new ExitListener());
		
		
		if(workmen.isFast()) {
			speedUp.setEnabled(false);
			speedUp.setText("<HTML><b><center>SHOES<br>(PURCHASED)</b></center></HTML>");
		}
		else if(game.getTotalFood() < 69) {
			speedUp.setEnabled(false);
			speedUp.setText("<HTML><b><center>SHOES<br>(69 Food)</b></center></HTML>");
		}
		if(workmen.isHarvestGod()) {
			harvestUp.setEnabled(false);
			harvestUp.setText("<HTML><b><center>GLOVES<br>(PURCHASED)</b></center></HTML>");
		}
		else if(game.getTotalStone() < 69) {
			harvestUp.setEnabled(false);
			harvestUp.setText("<HTML><b><center>GLOVES<br>(69 Stone)</b></center></HTML>");
		}
		if(workmen.clothedUp()) {
			clothesUp.setEnabled(false);
			clothesUp.setText("<HTML><b><center>SCARF<br>(PURCHASED)</b></center></HTML>");
		}
		else if(game.getTotalWood() < 69) {
			clothesUp.setEnabled(false);
			clothesUp.setText("<HTML><b><center>SCARF<br>(69 Wood)</b></center></HTML>");
		}
		
		
		//  adds everything to holder for what purpose? yes.
		holder.add(nameOfResource);
		holder.add(exit);
		holder.add(description);
		holder.add(speedUp);
		holder.add(harvestUp);
		holder.add(clothesUp);
		holder.add(imageGoesHere);
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
	
	private class UpgradeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == speedUp) {
				System.out.println("food = " + game.getTotalFood());
				if(game.getTotalFood() >= 69) {
					for(int i = 0; i < 69; i++) {
						i = game.removeResources(i, ResourceType.BERRY_BUSH);
						if(i == 0) {
							break;
						}
						i = game.removeResources(i, ResourceType.FISH);
						if(i == 0) {
							break;
						}
						i = game.removeResources(i, ResourceType.SALTY_FISH);
						if(i == 0) {
							break;
						}
						break;
					}
					if(thisWorker != null) {
						thisWorker.setFast(true);
						speedUp.setEnabled(false);
					}
					speedUp.setText("<HTML><b><center>SHOES<br>(PURCHASED)</b></center></HTML>");
				}
			}
			if(e.getSource() == harvestUp) {
				if(game.getTotalStone() >= 69) {
					game.removeResources(69, ResourceType.STONE);
					thisWorker.setHarvestGod(true);
					harvestUp.setEnabled(false);
					harvestUp.setText("<HTML><b><center>GLOVES<br>(PURCHASED)</b></center></HTML>");
				}
			}
			if(e.getSource() == clothesUp) {
				if(game.getTotalStone() >= 69) {
					game.removeResources(69, ResourceType.TREE);
					thisWorker.setClothed(true);
					clothesUp.setEnabled(false);
					clothesUp.setText("<HTML><b><center>SCARF<br>(PURCHASED)</b></center></HTML>");
				}
			}
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
		thisWorker = (Worker) o;
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
				+ workmen.getJob().y 
				+ "\nCarrying: " + workmen.getInventorySize());
		
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
