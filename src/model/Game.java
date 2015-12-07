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
import view.WinterScreen;

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
	
	// This variable keeps track of how much food the village has
	private int totalWood = 0;
	
	// This variable keeps track of how much food the village has
	private int totalStone = 0;
		
	// This variable keeps track of how much food the village has
	private int totalFood = 0;
	
	// This variable represents the maximum amount of a resource is possible
	private int totalMax = 0;
	
	// These variables are for changing seasons in-game (winter is coming)
	private int lengthOfSeasons = 90;
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
	
	//soft cap on workers
	public int softPopCap = 5;
	
	private Game game;
	
	/**************************************
	 *          Worker Constructor        *
	 **************************************/
	// Creates the game using a randomly generated map from map.java 
	public Game(Map theMap) {
		this.theMap = theMap;
		this.game = this;
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
//		for(int i = 0; i < 250; i++) {
//			((Storage)buildings.get(0)).addResource(ResourceType.TREE);
//		}
//		for(int i = 0; i < 20; i++) {
//			((Storage)buildings.get(0)).addResource(ResourceType.STONE);
//		}
		
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
		if(toBuild == BuildingPanel.BRIDGE_ID) {
			build = new HorizontalBridge(topLeftBuildingPoint);
		}
		if(toBuild == BuildingPanel.HOUSE_ID) {
			build = new House(topLeftBuildingPoint);
			//System.out.println("ADDED NEW HOUSE");
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
			//int sizeOfBerryBush = theMap.getBerryList().size();
			//for(int i = 0; i < sizeOfBerryBush; i++) {
			//	allOtherBuildings.add(theMap.getBerryList().get(i))
			//}
			ArrayList<Point> buildPoints = build.getPoints();
			for(int i = 0; i < allOtherBuildings.size(); i++) {
				for(int j = 0; j < buildPoints.size(); j++) {
					if(buildPoints.get(j).distance(allOtherBuildings.get(i))==0) {
						canBuild = false;
					}
				}
			}
			boolean canBuildCheck2 = true;
			if(build instanceof HorizontalBridge) {
				for(int i = 0; i < buildPoints.size(); i++) {
					if(!theMap.canBuildBridgesAt(buildPoints.get(i)))
						canBuildCheck2 = false;
				}
			}
			else {
				for(int i = 0; i < buildPoints.size(); i++) {
					if(!theMap.canBuildBuildingsAt(buildPoints.get(i)))
						canBuildCheck2 = false;
				}
			}
			
			if(canBuild&&canBuildCheck2) {
				buildings.add(build);
				//  magic numbers 
				if(build instanceof House) {
					removeResources(50, ResourceType.STONE);
					removeResources(120, ResourceType.TREE);
				}
				if(build instanceof Storehouse)
					removeResources(100, ResourceType.TREE);
				if(build instanceof HorizontalBridge)
					removeResources(50, ResourceType.STONE);
			
				return -1;
			}
		}
		return toBuild;
	}
	public int removeResources(int number, ResourceType type) {
		for(int i = 0; i < buildings.size(); i++) {
			if(number == 0)
				break;
			if(buildings.get(i) instanceof Storage) {
				while((((Storage)buildings.get(i)).hasResource(type))&&(number != 0)) {
					((Storage)buildings.get(i)).removeResource(type);
					number -= 1;
				}
			}
		}
		return number;
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
			//sets the harvestable variable
			theMap.setHarvestable();
			
			//adds workers based on pop cap
			
			int currentPop = list.size();
			if(currentPop < softPopCap && gameLength % 7 == 0){
				Random gen = new Random();
				int chance = gen.nextInt(100);
				Point TH = theMap.getTH();

				if(chance <25){
					Worker James = new KJG(new Point(TH.x,TH.y));
					list.add(James);
				}
				
				else if(chance <50){
					Worker James = new KJD(new Point(TH.x,TH.y));
					list.add(James);
				}
				
				else if(chance <75){
					Worker SleepingBeauty = new Brett(new Point(TH.x,TH.y));
					list.add(SleepingBeauty);
				}
			
				else if(chance <100){
					Worker BeardedWonder = new David(new Point(TH.x,TH.y));
					list.add(BeardedWonder);
					//  same
				}		
			}
			
			// Increment workers conditions every 5 seconds
			if((isWinter)&&(gameLength %5 == 0)) {
				list.incrementColdness();
				if(list.removeDead())
					setChange();
			}
			if((gameLength % 10) == 0) {
				list.incrementHunger();
				list.incrementFatigue();
				if(list.removeDead()) {
					setChange();
				}
			}
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).shouldGoHome(game)) {
					Buildable closestHouse = null;
					for(int j = 0; j < buildings.size(); j++) {
						if((buildings.get(j) instanceof House) || (buildings.get(j) instanceof TownHall)) {
							if(closestHouse == null) {
								closestHouse = buildings.get(j);
							}
							else {
								if(list.get(i).getPoint().distance(buildings.get(j).getClosestPoint(list.get(i).getPoint())) <
								   list.get(i).getPoint().distance(closestHouse.getClosestPoint(list.get(i).getPoint()))) {
									closestHouse = buildings.get(j);
								}		
							}
						}
						// A variable that will set is healing to true when myTask.isEmpty
						list.get(i).setBusy(true);

						list.get(i).setFoundHome(true);
						ShortestPathCalculator calc = new ShortestPathCalculator(theMap.getMapTiles(),buildings);
						list.get(i).toLocation(calc.getShortestPath(list.get(i).getPoint(), closestHouse.getClosestPoint(list.get(i).getPoint())));
						list.get(i).setJob(closestHouse.getClosestPoint(list.get(i).getPoint()));
						setChange();
					}
				}
			}
			int tempWood = 0;
			int tempStone = 0;
			int tempFood = 0;
			int tempMax = 0;
			int tempCap = 0;
			for(int i = 0; i < buildings.size(); i++) {
				if((buildings.get(i) instanceof Storage) || (buildings.get(i) instanceof TownHall)) {
					Storage storage = (Storage) buildings.get(i);
					tempWood += storage.getWoodCount();
					tempStone += storage.getStoneCount();
					tempFood += storage.getFoodCount(); 
					tempMax += storage.getCapacity();
				}
				if(buildings.get(i) instanceof House)
				tempCap+=5;
			}
			softPopCap = tempCap + 5;
			totalWood = tempWood;
			totalStone = tempStone;
			totalFood = tempFood;
			totalMax = tempMax;
			
			if(totalWood > totalMax) {
				totalWood = totalMax;
			}
			if(totalStone > totalMax) {
				totalStone = totalMax;
			}
			if(totalFood > totalMax) {
				totalFood = totalMax;
			}
			
			seasonsCounter++;
			
			// Either begin or end winter based on seasonsCounter
			if(seasonsCounter == lengthOfSeasons-2) {
				WinterScreen screen = new WinterScreen(isWinter);
			}
			if(seasonsCounter >= lengthOfSeasons) {
				isWinter = !isWinter;
				
				if(!isWinter){
					theMap.regenFood();
				}
				seasonsCounter = 0;
				//  AUTO SAVE ON WINTER COMPLETION OR START
				//  saveTheGame();
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
					
					//  Need to make sure there is a path to be able to get there... that has to be done otherwise it will cycle through
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

			//checks to see if they go back home to heal
			for(int i = 0; i < listSize; i++){
				if(list.get(i).shouldGoHome(game) && list.get(i).nextToJob()) {
					list.get(i).setIsHealing(true);
					list.get(i).setFoundHome(false);
					list.get(i).setDoneHealing(false);
				}
			}
			
			boolean doneWithHunger = false;
			boolean doneWithColdness = false;
			boolean doneWithFatigue = false;
			// Checks to see if they're done healing
			for(int i = 0; i < listSize; i++) {
				if(list.get(i).isHealing()) {
					if(list.get(i).doneHealing()) {
						list.get(i).setIsHealing(false);
						list.get(i).setBusy(false);
						if(list.get(i).getInventorySize() > 0) {
							list.get(i).resetStorage();
							list.get(i).goToStorage(theMap);
						//	list.get(i).doTheWork(theMap.getMapTiles()[list.get(i).getJob().y][list.get(i).getJob().x],theMap);
						}
						System.out.println("DONE HEALING");
					}
					else {
						if(list.get(i).getFatigue() >= 1) {
							list.get(i).decrementFatigue();
						}
						else {
							doneWithFatigue = true;
						}
						if((totalFood > 1) && (list.get(i).getHunger() >= 1)) {
							list.get(i).decrementHunger();
							int number = 2;
							while(true) {
								number = removeResources(number, ResourceType.BERRY_BUSH);
								if(number == 0) {
									break;
								}
								number = removeResources(number, ResourceType.FISH);
								if(number == 0) {
									break;
								}
								number = removeResources(number, ResourceType.SALTY_FISH);
								if(number == 0) {
									break;
								}
								break;
							}
						}
						else {
							doneWithHunger = true;
						}
						if((totalWood > 9) && (list.get(i).getColdness() >= 1)) {
							list.get(i).decrementColdness();
							removeResources(10, ResourceType.TREE);
						}
						else {
							doneWithColdness = true;
						}
						if(doneWithFatigue && doneWithHunger && doneWithColdness) {
							list.get(i).setDoneHealing(true);
						}
					}
				}
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
					dummy.doTheWork(theMap.getMapTiles()[dummy.getJob().y][dummy.getJob().x],theMap, game);
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
	public boolean isWinter() {
		return isWinter;
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
	
	public int getTotalWood() {
		return totalWood;
	}
	
	public int getTotalStone() {
		return totalStone;
	}
	
	public int getTotalFood() {
		return totalFood;
	}
	
	public int getTotalMax() {
		return totalMax;
	}
}
