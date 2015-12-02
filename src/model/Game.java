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

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import javax.swing.Timer;

import view.BuildingPanel;
import view.GGScreen;

//Our game class extends Observable, and will notify the other classes when an event occurs
public class Game extends Observable implements Serializable {
	
	/**************************************
	 *          Instance Variables        *
	 **************************************/
	
	// Creates the map
	private Map theMap;
	
	// This list holds all of the living workers
	private ListOfWorkers list;
	
	private ArrayList<Buildable> buildings = new ArrayList<>();
	
	//  This is the worker queue
	private WorkQueue workQueue = new WorkQueue();

	// This variable keeps track of how long the game has been played
	private int gameLength = 0;
	
	// These variables are for changing seasons in-game (winter is coming)
	private int lengthOfSeasons = 60;
	private int seasonsCounter;
	private boolean isWinter;
	private short wintersSurvived;
	
	// gameTimer ticks every second the game has been played and increments gameLength
	private Timer gameTimer = new Timer(1000, new GameTimerListener());
	
	// SpeedMeter is responsible for NPC movement
	private Timer SpeedMeter = new Timer(200, new MovementTimerListener());
	
	// Our current max number of workers is 50
	public static final int MAX_NUMBER_OF_WORKERS = 50;

	public static final int NORMAL_SPEED = 1;
	
	/**************************************
	 *          Worker Constructor        *
	 **************************************/
	// Creates the game using a randomly generated map from map.java 
	public Game(Map theMap) {
		this.theMap = theMap;
		list = new ListOfWorkers(MAX_NUMBER_OF_WORKERS);
		ArrayList<Worker> init = theMap.getInitialWorkers();
		int initSize = init.size();
		for(int i = 0; i < initSize; i++) {
			list.add(init.get(i));
		}
		ArrayList<Buildable> initBuild = theMap.getStorageList();
		for(int i = 0; i < initBuild.size(); i++) {
			buildings.add(initBuild.get(i));
		}
		setChange();
		SpeedMeter.start();
		gameTimer.start();
	}

	public void startTimers() {
		SpeedMeter = new Timer(200, new MovementTimerListener());
		SpeedMeter.start();
		gameTimer = new Timer(1000, new GameTimerListener());
		gameTimer.start();
	}
	
	public void setChange() {
		setChanged();
		ThePackage theFUCKINGPackage = new ThePackage(buildings, list);
		notifyObservers(theFUCKINGPackage);
	}
	
	public void addBuilding(Buildable building) {
		buildings.add(building);
		setChange();
	}
	public int addNewBuildingUsingID(int toBuild, Point topLeftBuildingPoint) {
		Buildable build = null;
		if(toBuild == BuildingPanel.BRIDGE_H_ID) {
			build = new HorizontalBridge(topLeftBuildingPoint);
		}
		if(toBuild == BuildingPanel.BRIDGE_V_ID) {
			build = new VerticalBridge(topLeftBuildingPoint);
		}
		if(toBuild == BuildingPanel.HOUSE_ID) {
			build = new House(topLeftBuildingPoint);
		}
		if(toBuild == BuildingPanel.STOREHOUSE_ID) {
			build = new Storehouse(topLeftBuildingPoint);
		}
		if(build != null) {
			boolean canBuild = true;
			ArrayList<Point> allOtherBuildings = new ArrayList<>();
			for(int i = 0; i < buildings.size(); i++) {
				allOtherBuildings.addAll(buildings.get(i).getPoints());
			}
			ArrayList<Point> buildPoints = build.getPoints();
			for(int i = 0; i < allOtherBuildings.size(); i++) {
				for(int j = 0; j < buildPoints.size(); j++) {
					if(buildPoints.get(j).distance(allOtherBuildings.get(i))==0) {
						canBuild = false;
					}
				}
			}
			if(canBuild) {
				buildings.add(build);
				return -1;
			}
		}
		return toBuild;
	}
	public ArrayList<Buildable> getBuildings() {
		return buildings;
	}
	
	public ThePackage getThePackage() {
		return new ThePackage(buildings, list);
	}
	
	public WorkQueue getWorkQueue() {
		return workQueue;
	}
	/**************************************
	 *   Adding An Job to the Queue       *
	 **************************************/
	public void addJob(Job theJob) {
		workQueue.add(theJob);
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
			//Updates all of the resources
			theMap.setHarvestable();
			
			// Increment workers conditions every 5 seconds
			if((gameLength % 10) == 0) {
				list.incrementHunger();
				list.incrementFatigue();
				if(isWinter)
					list.incrementColdness();
				if(list.removeDead()) {
					setChange();
				}	
			}
			seasonsCounter++;
			
			// Either begin or end winter based on seasonsCounter
			if(seasonsCounter >= lengthOfSeasons) {
				isWinter = !isWinter;
				seasonsCounter = 0;
				//  AUTO SAVE ON WINTER COMPLETION OR START
				saveTheGame();
				if(!isWinter)
					wintersSurvived ++;
			}
			gameLength++;
			if(!workQueue.isEmpty()) {
				int queueSize = workQueue.size();
				for(int i = 0; i < queueSize; i++) {
					Job dest = workQueue.getFirst();
					if(dest == null)
						break;
					
					Worker jobDoer = list.findClosest(dest.getLocation());
					
					//  everyone is supa busy
					if(jobDoer == null) {
						break;
					}
					
					//  Need to make sure there is a path to beable to get there... that has to be done otherwise it will cycle through
					//  wanting to get salty fish when it's impossible leading to nothing happening Q.Q
					ShortestPathCalculator calc = new ShortestPathCalculator(getMap(), buildings);
					ArrayList<Direction> toThere = calc.getShortestPath(jobDoer.getPoint(), dest.getLocation());
					//  didn't find a path
					//System.out.printf("Count:%d Job: %s\n", i, dest.getName());
					if(toThere.isEmpty()){
						workQueue.removeJob(dest);
						continue;
					}
					jobDoer.toLocation(toThere);
					workQueue.removeJob(dest);
					jobDoer.setJob(dest.location);
					jobDoer.setBusy(true);
				}
			}
			
			int listSize = list.size();
			if(listSize == 0) {
				GGScreen wp = new GGScreen(gameLength, wintersSurvived);
				gameTimer.stop();
				SpeedMeter.stop();
			}

			//checks to see if they have full resource
			for(int i = 0; i <listSize; i++){
				if(list.get(i).atMaxCap()){
					list.get(i).goToStorage(theMap);
				}
			}
			
			//checks for idle
			for(int i = 0; i <listSize; i++){
				if(!list.get(i).isBusy()){
					list.get(i).getClosestPreference(theMap);
				}
			}
			
			//checks to see if next to the point it was sent to
			for(int i = 0; i <listSize; i++){
				Worker dummy = list.get(i);
				if(dummy.nextToJob()){
					dummy.doTheWork(theMap.getMapTiles()[dummy.getJob().y][dummy.getJob().x]);
				}
			}
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
			ThePackage theFUCKINGPackage = new ThePackage(buildings, list);
			notifyObservers(theFUCKINGPackage);
			animalTic++;
		}
	}

	public MapTile[][] getMap() {
		// TODO Auto-generated method stub
		return theMap.getMapTiles();
	}

	public void saveTheGame() {
		try {
			FileOutputStream fos = new FileOutputStream("GameData");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			fos.close();
			oos.close();
			}
			catch(Exception saveProbs) {
				saveProbs.printStackTrace();
		}
	}

	public void changeTimers(int speed) {
		SpeedMeter.stop();
		gameTimer.stop();
		gameTimer = new Timer((NORMAL_SPEED*1000)/speed, new GameTimerListener());
		SpeedMeter = new Timer((NORMAL_SPEED*250)/speed,  new MovementTimerListener());
		gameTimer.start();
		SpeedMeter.start();
	}
}
