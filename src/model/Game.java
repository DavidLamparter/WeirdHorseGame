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

package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.Timer;

public class Game extends Observable{
	private Map theMap;
	private ListOfWorkers list;
	
	//  TIMERS  //  ALL OF THE TIMERS  // 
	private Timer hungerMeter = new Timer(5000, new HungerTimerListener());
	private Timer sleepMeter = new Timer(5000, new SleepTimerListener());
	private Timer coldMeter = new Timer(5000, new ColdTimerListener());
	private Timer gameTimer = new Timer(1000, new GameTimerListener());
	private Timer SpeedMeter = new Timer(50, new MovementTimerListener());
	
	//  TICS
	private int gameLength = 0;
	private int lengthOfSeasons;
	private int seasonsCounter;
	private boolean isWinter;
	private short wintersSurvived;
	
	//  CONSTRUCTOR
	public static final int MAX_NUMBER_OF_WORKERS = 50;
	public Game(Map theMap) {
		this.theMap = theMap;
		list = new ListOfWorkers(MAX_NUMBER_OF_WORKERS);
	}
	public ListOfWorkers getList() {
		return list;
	}
	/* There are three ways we can do the image loading
	 * 1st we can make an image holder that will take in a map and hold every image for each part of the map.
	 * 2nd we can have no. 1 in the map and clear all of the saved images before saving to avoid non serializable
	 * 3rd we can load the image every time we repaint
	 * 
	 * I personally like no 1 because it's an array of images for the map, However I don't know
	 * how to animate the peeps so that could be a potential problem
	 */
	private class MovementTimerListener implements ActionListener {
		private int animalTic = 0;
		@Override
		public void actionPerformed(ActionEvent e) {
			if(animalTic > 3) {
				animalTic = 0;
				//  move the animals if they are going to be added
			}
			list.moveYourAsses();
			setChanged();
			notifyObservers();
			animalTic++;
		}
	}
	private class HungerTimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			list.incrementHunger();
		}
	}
	private class SleepTimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			list.incrementSleep();
		}
	}
	private class ColdTimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			list.incrementColdness();
		}
	}
	private class GameTimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			seasonsCounter++;
			if(seasonsCounter >= lengthOfSeasons) {
				isWinter = !isWinter;
				seasonsCounter = 0;
				if(!isWinter)
					wintersSurvived ++;
			}
			gameLength++;
		}
	}
}
