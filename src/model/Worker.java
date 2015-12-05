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
|  Description:  This program creates the Worker object that will represent our agents that can
|				 collect resources. Each worker is "born" with a set of preferences that they
|                default to if not given any orders by the player.
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public abstract class Worker extends Observable implements Serializable {
	
	/**************************************
	 *          Instance Variables        *
	 **************************************/
	
	// Conditions (bad stuff) begin at 0, and increment to dangerous levels
	private double hunger;
	private double fatigue;
	private double coldness;
	
	// The current X and Y position this worker is located at
	private int XPos;
	private int YPos;
	
	// The last X and Y position the worker was located at
	private int oldXPos;
	private int oldYPos;
	
	// Aspects (good/neutral stuff) begin at max and decrement to dangerous levels
	private int happiness;
	private int carryingCapacity;
	
	// Tool represents what tool the worker is holding
	private Tool tool;
	
	// isAlive represents whether or not the worker is alive
	private boolean isAlive;
	
	// isBusy represents if the worker is currently performing a task
	private boolean isBusy;
	
	// inDanger represents if the worker should go home to eat/sleep
	private boolean goHome;
	
	// isHealing represents if the worker is heading home to heal
	private boolean isHealing;
	
	// foudHome is true if a worker has begun their journey home
	private boolean foundHome;
	
	// Determines if a worker is done healing
	private boolean doneHealing;
	
	// Stores the list of directions for a specific task
	private ArrayList<Direction> myTask = new ArrayList<>();
	
	// Stores the last direction used by the worker (for animation use)
	private Direction last;
	
	// keeps track of the workers preferance
	private ResourceType preference;
	
	private Point job;
	
	private Storage storage;
	
	private ResourceType[] inventory;
	/**************************************
	 *          Worker Constructor        *
	 **************************************/
	
	//  Worker is constructed with its starting position as a parameter
	public Worker(Point currentLocation) {
		
		//used to determine what they are putting and where
		inventory = new ResourceType[20];
		job = null;
		storage = null;
		
		// Conditions being at 0, and increment to dangerous levels
		hunger = 0;
		fatigue = 0;
		coldness = 0;
		
		// Aspects begin at max and decrement to dangerous levels
		happiness = 100;
		carryingCapacity = 20;
		
		// Current tool begins at none
		tool = Tool.NONE;
		
		// isAlive begins at true
		isAlive = true;
		goHome = false;
		isHealing = false;
		
		// Set the current location of the worker when spawned
		XPos = currentLocation.x;
		YPos = currentLocation.y;
		
		// Sets the Preference
		double randomize = Math.random();
		
		if(randomize < .33){
			preference = ResourceType.TREE;
		}
		else if(randomize < .66){
			preference = ResourceType.STONE;
		}
		else if(randomize < 1){
			preference = ResourceType.BERRY_BUSH;
		}
	}
	
	/**************************************************
	 *   Getters and Setters for Instance Variables   *
	 **************************************************/
	
	public boolean atMaxCap(){
		return carryingCapacity <= 0;
	}
	
	public double getHunger() {
		return hunger;
	}
	
	public double getFatigue() {
		return fatigue;
	}
	
	public double getColdness() {
		return coldness;
		
	}
	
	public int getHappiness() {
		return happiness;
	}
	
	public int getCarryingCapacity() {
		return carryingCapacity;
	}
	
	public Tool getTool() {
		return tool;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public boolean isBusy() {
		return isBusy;
	}
	
	public boolean isHealing() {
		return isHealing;
	}
	
	public boolean foundHome() {
		return foundHome;
	}
	
	public int getX() {
		return XPos;
	}
	
	public int getY() {
		return YPos;
	}
	
	public int getOldX() {
		return oldXPos;
	}
	
	public int getOldY() {
		return oldYPos;
	}
	
	public Point getOldPoint() {
		return new Point(oldXPos, oldYPos);
	}
	
	public Point getPoint() {
		return new Point(XPos, YPos);
	}
	
	public Direction getLast() {
		return last;
	}
	
	public boolean getGoHome() {
		return goHome;
	}
	
	public ResourceType getPreference() {
		return preference;
	}
	public Point getJob(){
		return job;
	}
	public void setJob(Point point){
		job = point;
	}
	
	/**************************************
	 *              Setters               *
	 **************************************/
	
	public void setTool(Tool tool) {
		this.tool = tool;
	}
	
	public void setGoHome(boolean home) {
		goHome = home;
	}
	
	public void setIsHealing(boolean heal) {
		isHealing = heal;
	}
	
	public void setFoundHome(boolean home) {
		foundHome = home;
	}
	
	public void setDoneHealing(boolean heal) {
		doneHealing = heal;
	}
	
	/**************************************
	 *        Adders for Conditions       *
	 **************************************/
	
	// Hunger helper method for sub classes
	protected void addHunger(double hunger) {
		this.hunger += hunger;
		setChanged();
		notifyObservers();
	}
	
	public void addHunger() {
		// If hunger rises above 10, this worker may die from starvation
		if(hunger >= 10) {
			inDanger(hunger);
		}
		// If hunger is above 5, workers should run home
		else if(hunger >= 4) {
			goHome = true;
		}
		addHunger(1.0);
	}
	
	// Fatigue helper method for sub classes
	protected void addFatigue(double fatigue) {
		this.fatigue += fatigue;
		setChanged();
		notifyObservers();
	}
	
	public void addFatigue() {
		// If fatigue rises above 10, this worker may die from exhaustion
		if(fatigue >= 10) {
			inDanger(fatigue);
		}
		// If fatigue is above 5, workers should run home
		else if(fatigue >= 4) {
			goHome = true;
		}
		addFatigue(1.0);
	}
	
	// Coldness helper method for sub classes
	protected void addColdness(double coldness) {
		this.coldness += coldness;
		setChanged();
		notifyObservers();
	}
	
	public void addColdness() {
		// If coldness rises above 10, this worker may die from frostbite
		if(coldness >= 10) {
			inDanger(coldness);
		}
		// If coldness is above 5, workers should run home
		else if(coldness >= 4) {
			goHome = true;
		}
		addColdness(1.0);
	}
	
	/**************************************
	 *     Subtractors for Conditions     *
	 **************************************/
	
	public void decrementHunger() {
		if(hunger >= 1) {
			hunger -= 1;
		}
		else {
			hunger = 0;
		}
	}
	
	public void decrementFatigue() {
		if(fatigue >= 1) {
			fatigue -= 1;
		}
		else {
			fatigue = 0;
		}
	}
	
	public void decrementColdness() {
			coldness = 0;
	}
	
	public boolean doneHealing() {
		return doneHealing;
	}
	
	/**************************************
	 *       Subtractors for Aspects      *
	 **************************************/
	
	// Happiness helper method for sub classes
	protected void subtractHappiness(double happiness) {
		this.happiness -= happiness;
	}
	
	public void subtractHappiness() {
		subtractHappiness(1.0);
	}
	
	public void subtractCarryingCapacity() {
		carryingCapacity -= 1;
	}
	
	/**************************************
	 *          Death Calculator          *
	 **************************************/
	
	// This method is ((TRIGGERED)) when a worker hits the cap of a condition meter. 
	// It will calculate if the worker dies, or if they somehow survive another round
	public void inDanger(double status) {
		double dubStatus = (status / 100 - 0.1) * Math.pow(1.25,(status));
		double rand = Math.random();
		if(rand > (0.66 - dubStatus)) {
			isAlive = false; 
			setChanged();
			notifyObservers();
		}
	}
	
	/****************************************************
	 *          To be busy or not || next to job        *
	 ****************************************************/
	
	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}
	
	public boolean nextToJob(){
		return job.getLocation().distance(new Point(XPos,YPos)) <= 1.4;
		}
		
	/**************************************
	 *          Movement Methods          *
	 **************************************/
	
	// This method is used to pass a path to the worker to walk along
	public void toLocation(ArrayList<Direction> directions) {
		myTask = directions;
		System.out.printf("A TASK OF SIZE %d HAS BEEN FOUND\n", myTask.size());
	}
	// This method performs the actual movement for the worker
	public void move() {
		if(!myTask.isEmpty()) {
			oldYPos = YPos;
			oldXPos = XPos;
			Direction toGo = myTask.get(0);
			myTask.remove(0);
			if(toGo.equals(Direction.NORTH)) {
				YPos--;
				last = Direction.NORTH;
			}
			if(toGo.equals(Direction.SOUTH)) {
				YPos++;
				last = Direction.SOUTH;
			}
			if(toGo.equals(Direction.EAST)) {
				XPos++;
				last = Direction.EAST;
			}
			if(toGo.equals(Direction.WEST)) {
				XPos--;
				last = Direction.WEST;
			}
		}
		//  Will need to be something else an intermediary but this will be cool to see goons run around
		else {
		setChanged();
		notifyObservers();
		}
	}

	public void getClosestPreference(Map theMap) {
		double distance = Double.MAX_VALUE;
		Point closest = new Point();
		ShortestPathCalculator calc = new ShortestPathCalculator(theMap.getMapTiles(), theMap.getBuildings());
		ArrayList<Direction> goingToGo;
		int breakCounter = 0;
		
		//for if the preference is berry
		if(preference == ResourceType.BERRY_BUSH){			
			ArrayList<Job> BerryList = theMap.getBerryList();
			int size = BerryList.size();
					
			for(int i = 0; i < size; i++){
				Resource temp = BerryList.get(i).resource;
				if(temp.getHarvestable()){
			int BerryX = BerryList.get(i).location.x;
			int BerryY = BerryList.get(i).location.y;
			
			//double distanceToResource = Math.sqrt(Math.pow((XPos - BerryX),2) + Math.pow((YPos - BerryY),2));
			double distanceToResource = getPoint().distance(BerryList.get(i).location);
			
			if(distanceToResource < distance){
				goingToGo = calc.getShortestPath(getPoint(), new Point(BerryX,BerryY));
				if(goingToGo.size()!=0) {
					distance = distanceToResource;
					closest = new Point(BerryX,BerryY);
				}
			}
				}
			}
		}
		
		//for if the preference is Fish
		if(preference == ResourceType.FISH){			
			ArrayList<Job> FishList = theMap.getFishList();
			int size = FishList.size();
					
			for(int i = 0; i < size; i++){
				Resource temp = FishList.get(i).resource;
				if(temp.getHarvestable()){
			int FishX = FishList.get(i).location.x;
			int FishY = FishList.get(i).location.y;
			
//			double distanceToResource = Math.sqrt(Math.pow((XPos - FishX),2) + Math.pow((YPos - FishY),2));
				double distanceToResource = getPoint().distance(FishList.get(i).location);
		
			if(distanceToResource < distance){
				goingToGo = calc.getShortestPath(getPoint(), new Point(FishX,FishY));
				if(goingToGo.size()!=0) {
					distance = distanceToResource;
					closest = new Point(FishX,FishY);
				}
			}
				}
			}
		}

		//for if the preference is Tree
		if(preference == ResourceType.TREE){			
			ArrayList<Job> TreeList = theMap.getTreeList();
			int size = TreeList.size();
					
			for(int i = 0; i < size; i++){
				
				Resource temp = TreeList.get(i).resource;

				if(temp.getHarvestable()){
			int TreeX = TreeList.get(i).location.x;
			int TreeY = TreeList.get(i).location.y;
			
			//double distanceToResource = Math.sqrt(Math.pow((XPos - TreeX),2) + Math.pow((YPos - TreeY),2));
			
			double distanceToResource = getPoint().distance(TreeList.get(i).location);

			if(distanceToResource < distance){
				goingToGo = calc.getShortestPath(getPoint(), new Point(TreeX,TreeY));
				if(goingToGo.size()!=0) {
				distance = distanceToResource;
				closest = new Point(TreeX,TreeY);
				}
			}
				}
			}
		}
		
		//for if the preference is Tree
		if(preference == ResourceType.STONE){			
			ArrayList<Job> StoneList = theMap.getStoneList();
			int size = StoneList.size();
			
			for(int i = 0; i < size; i++){
				Resource temp = StoneList.get(i).resource;
				if(temp.getHarvestable()){
			int StoneX = StoneList.get(i).location.x;
			int StoneY = StoneList.get(i).location.y;
			
			//double distanceToResource = Math.sqrt(Math.pow((XPos - StoneX),2) + Math.pow((YPos - StoneY),2));
			
			double distanceToResource = getPoint().distance(StoneList.get(i).location);
			
			if(distanceToResource < distance){
				goingToGo = calc.getShortestPath(getPoint(), new Point(StoneX,StoneY));
				if(goingToGo.size()!=0) {
				distance = distanceToResource;
				closest = new Point(StoneX,StoneY);
				}
			}
				}
			}
		}
		//System.out.println("X for res: " + closest.x + " Y for res: " + closest.y + "\n XPos " + XPos + "YPos " + YPos);
		if(closest!=null) {
			toLocation(calc.getShortestPath(getPoint(), new Point(closest)));
			System.out.println("FINDING PREFERENCE ^\n");
			job = closest;
			isBusy = true;
			setChanged();
			notifyObservers();
		}
	}	
	
	private boolean onlyOneStorageCall = false;
	public void goToStorage(Map theMap){
		if(onlyOneStorageCall)
			return;
		double distance = Double.MAX_VALUE;
		Point closest = new Point();
	
			ArrayList<Storage> storageList = theMap.getStorageList();
			int size = theMap.getStorageList().size();
					
			for(int i = 0; i < size; i++){
			Point closestStorage =  storageList.get(i).getClosestPoint(getPoint());
			int StorageX = closestStorage.x;
			int StorageY = closestStorage.y;
			
			//double distanceToResource = Math.sqrt(Math.pow((XPos - BerryX),2) + Math.pow((YPos - BerryY),2));
			double distanceToResource = getPoint().distance(storageList.get(i).getPoints().get(0));
			
			if(distanceToResource < distance){
				distance = distanceToResource;
				closest = new Point(StorageX,StorageY);
				storage = storageList.get(i);
			}
		}
			ShortestPathCalculator calc = new ShortestPathCalculator(theMap.getMapTiles(),
					theMap.getBuildings());
			toLocation(calc.getShortestPath(getPoint(), new Point(closest)));
			System.out.println("STORAGE ^\n");
			job = closest;
			isBusy = true;
			onlyOneStorageCall = true;
			setChanged();
			notifyObservers();
	}
	
	/******************************************************
	*                 Harvest and deposit                 *
	******************************************************/
	
	public void doTheWork(MapTile tile, Map theMap){
		if(isHealing)
			return;
		isBusy = true;
		int i = 0;
		
		//if next to a storage
		if(tile.getResource().getResourceT() == ResourceType.NONE){
			while (i < 20){
				storage.addResource(inventory[i]);
				i++;
			}
			carryingCapacity = 20;
			inventory = new ResourceType[20];
			isBusy = false;
			onlyOneStorageCall = false;
		}
		
		//if next to job resource
		else{
			if(tile.getResource().getHarvestable()) {
				tile.getResource().subResource(1);
				if(carryingCapacity > 0)
				inventory[carryingCapacity - 1] = tile.getResource().getResourceT();
				subtractCarryingCapacity();
			}
			else {
				//  it is not harvestable so GO HOME
				goToStorage(theMap);
			}
		}
	}
	public int getInventorySize() {
		return 20-carryingCapacity;
	}

	public String animationFrameFileName() {
		// TODO Auto-generated method stub
		return "./Graphics/Workers/male_" + 1 + ".png";
	}
}

/******************************************************
*~~~~~~~~~~~~~~~~~~~~ BRETT CLASS ~~~~~~~~~~~~~~~~~~~~*
******************************************************/


class Brett extends Worker{
	
	/**************************************
	 *           Brett Constructor        *
	 **************************************/
	
	//  Worker is constructed with its starting position as a parameter
	public Brett(Point currentLocation) {
		super(currentLocation);
	}
	
	// This worker increments fatigue at a faster rate than other workers
	@Override
	public void addFatigue() {
		// If fatigue rises above 10, this worker may die from exhaustion
		if(getFatigue() >= 10) {
			inDanger(getFatigue());
		}
		addFatigue(1.5);
	}
}


/******************************************************
*~~~~~~~~~~~~~~~~~~~~ DAVID CLASS ~~~~~~~~~~~~~~~~~~~~*
******************************************************/


class David extends Worker {

	/**************************************
	 *          David Constructor         *
	 **************************************/
	
	//  Worker is constructed with its starting position as a parameter
	public David(Point currentLocation) {
		super(currentLocation);
	}
	
	// This worker increments hunger at a faster rate than other workers
	@Override
	public void addHunger() {
		// If hunger rises above 10, this worker may die from starvation
		if(getHunger() >= 10) {
			inDanger(getHunger());
		}
		addHunger(1.5);
	}
}


/******************************************************
*~~~~~~~~~~~~~~~~~~~~~ KJD CLASS ~~~~~~~~~~~~~~~~~~~~~*
******************************************************/


class KJD extends Worker {
	
	/**************************************
	 *           KJD Constructor          *
	 **************************************/
	
	//  Worker is constructed with its starting position as a parameter
	public KJD(Point currentLocation) {
		super(currentLocation);
	}
	
	// This worker decrements happiness at a faster rate than other workers
	@Override
	public void subtractHappiness() {
		subtractHappiness(1.5);
	}
}


/******************************************************
*~~~~~~~~~~~~~~~~~~~~~ KJG CLASS ~~~~~~~~~~~~~~~~~~~~~*
******************************************************/


class KJG extends Worker{

	/**************************************
	 *           KJG Constructor          *
	 **************************************/
	
	//  Worker is constructed with its starting position as a parameter
	public KJG(Point currentLocation) {
		super(currentLocation);
	}
	
	// This worker increments coldness at a faster rate than other workers
	@Override
	public void addColdness() {
		// If coldness rises above 10, this worker may die from frostbite
		if(getColdness() >= 10) {
			inDanger(getColdness());
		}
		addColdness(1.5);
	}
}

