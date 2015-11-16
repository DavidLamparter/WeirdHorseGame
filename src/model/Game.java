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
|  Description:  This program creates the observable game class that will construct the game itself,
|                using all of the other files. Game.java uses a timer to keep track of game length
|                and to trigger events. It also holds a list of workers that exist, and manages their
|                status.
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import javax.swing.Timer;

//Our game class extends Observable, and will notify the other classes when an event occurs
public class Game extends Observable{
	
	/**************************************
	 *          Instance Variables        *
	 **************************************/
	
	// Creates the map
	private Map theMap;
	
	// This list holds all of the living workers
	private ListOfWorkers list;

	// This variable keeps track of how long the game has been played
	private int gameLength = 0;
	
	// These variables are for changing seasons in-game (winter is coming)
	private int lengthOfSeasons;
	private int seasonsCounter;
	private boolean isWinter;
	private short wintersSurvived;
	
	// gameTimer ticks every second the game has been played and increments gameLength
	private Timer gameTimer = new Timer(1000, new GameTimerListener());
	
	// SpeedMeter is responsible for NPC movement
	private Timer SpeedMeter = new Timer(50, new MovementTimerListener());
	
	// Our current max number of workers is 50
	public static final int MAX_NUMBER_OF_WORKERS = 50;
	
	/**************************************
	 *          Worker Constructor        *
	 **************************************/
	// Creates the game using a randomly generated map from map.java 
	public Game(Map theMap) {
		this.theMap = theMap;
		list = new ListOfWorkers(MAX_NUMBER_OF_WORKERS);
		ArrayList<Worker> init = theMap.getInitialWorkers();
		for(int i = 0; i < init.size(); i++) {
			list.add(init.get(i));
		}
		setChanged();
		notifyObservers(list);
		SpeedMeter.start();
	}
	public void setChange() {
		setChanged();
		notifyObservers(list);
	}
	
	/**************************************
	 *   Getters for Instance Variables   *
	 **************************************/
	
	public ListOfWorkers getList() {
		return list;
	}
	
	/**************************************
	 *       Timer ActionListeners        *
	 **************************************/
	
	// This timer keeps track of the game play time, and initiates certain events
	// based on gameLength
	private class GameTimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			// Increment workers conditions every 5 seconds
			if((gameLength % 5) == 0) {
				list.incrementHunger();
				list.incrementFatigue();
				list.incrementColdness();
			}
			seasonsCounter++;
			
			// Either begin or end winter based on seasonsCounter
			if(seasonsCounter >= lengthOfSeasons) {
				isWinter = !isWinter;
				seasonsCounter = 0;
				if(!isWinter)
					wintersSurvived ++;
			}
			gameLength++;
		}
	}
	
	// This timer is responsible for moving NPC's. We may incorporate animal movement
	// in the future here
	private class MovementTimerListener implements ActionListener {
		
		private int animalTic = 0;
		
		@Override
		public void actionPerformed(ActionEvent e) {
		// This if-statement is for animal movement if we add that to our game
			if(animalTic > 3) {
				animalTic = 0;
			}
			list.moveWorkers();
			setChanged();
			notifyObservers(list);
			animalTic++;
		}
	}

	public MapTile[][] getMap() {
		// TODO Auto-generated method stub
		return theMap.getMapTiles();
	}
}
