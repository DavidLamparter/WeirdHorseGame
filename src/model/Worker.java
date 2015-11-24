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
import java.util.ArrayList;

public abstract class Worker{
	
	/**************************************
	 *          Instance Variables        *
	 **************************************/
	
	// Conditions (bad stuff) begin at 0, and increment to dangerous levels
	private int hunger;
	private int fatigue;
	private int coldness;
	
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
	
	// Stores the list of directions for a specific task
	private ArrayList<Direction> myTask = new ArrayList<>();
	
	// Stores the last direction used by the worker (for animation use)
	private Direction last;
	
	// keeps track of the workers preferance
	private ResourceType preference;
	
	/**************************************
	 *          Worker Constructor        *
	 **************************************/
	
	//  Worker is constructed with its starting position as a parameter
	public Worker(Point currentLocation) {
		
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
		
		// Set the current location of the worker when spawned
		XPos = currentLocation.x;
		YPos = currentLocation.y;
		
		// Sets the Preference
		double randomize = Math.random();
		
		if(randomize < .33){
			preference = ResourceType.TREE;
		}
		if(randomize < .66){
			preference = ResourceType.STONE;
		}
		if(randomize < .83){
			preference = ResourceType.BERRY_BUSH;
		}
		if(randomize < 1){
			preference = ResourceType.FISH;
		}
	}
	
	/**************************************
	 *   Getters for Instance Variables   *
	 **************************************/
	
	public int getHunger() {
		return hunger;
	}
	
	public int getFatigue() {
		return fatigue;
	}
	
	public int getColdness() {
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
	
	/**************************************
	 *          Setter for tool           *
	 **************************************/
	
	public void setTool(Tool tool) {
		this.tool = tool;
	}
	
	/**************************************
	 *        Adders for Conditions       *
	 **************************************/
	
	// Hunger helper method for sub classes
	protected void addHunger(double hunger) {
		this.hunger += hunger;
	}
	
	public void addHunger() {
		// If hunger rises above 10, this worker may die from starvation
		if(hunger >= 10) {
			inDanger(hunger);
		}
		addHunger(1.0);
	}
	
	// Fatigue helper method for sub classes
	protected void addFatigue(double fatigue) {
		this.fatigue += fatigue;
	}
	
	public void addFatigue() {
		// If fatigue rises above 10, this worker may die from exhaustion
		if(fatigue >= 10) {
			inDanger(fatigue);
		}
		addFatigue(1.0);
	}
	
	// Coldness helper method for sub classes
	protected void addColdness(double coldness) {
		this.coldness += coldness;
	}
	
	public void addColdness() {
		// If coldness rises above 10, this worker may die from frostbite
		if(coldness >= 10) {
			inDanger(coldness);
		}
		addColdness(1.0);
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
	
	// This method is TRIGGERED when a worker hits the cap of a condition meter. 
	// It will calculate if the worker dies, or if they somehow survive another round
	public void inDanger(int status) {
		double dubStatus = (((double) status) / 100 - 0.1) * Math.pow(1.25,(status));
		double rand = Math.random();
		if(rand > (0.66 - dubStatus))
			isAlive = false;
	}
	
	/**************************************
	 *          To be busy or not         *
	 **************************************/
	
	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}
	
	/**************************************
	 *          Movement Methods          *
	 **************************************/
	
	// This method is used to pass a path to the worker to walk along
	public void toLocation(ArrayList<Direction> directions) {
		myTask = directions;
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
		else
			isBusy = false;
	}

	public void getClosestPreference(Map theMap) {
		double distance = 5000;
		Point closest = new Point();

		//for if the preference is berry
		if(preference == ResourceType.BERRY_BUSH){			
			ArrayList<Point> BerryList = theMap.getBerryList();
			int size = BerryList.size();
					
			for(int i = 0; i < size; i++){

			int BerryX = BerryList.get(i).x;
			int BerryY = BerryList.get(i).y;
			
			double distanceToResource = Math.sqrt(Math.pow((XPos - BerryX),2) + Math.pow((YPos - BerryY),2));
			
			if(distanceToResource < distance){
				distance = distanceToResource;
				closest = new Point(BerryX,BerryY);
			}
			}
		}
		
		//for if the preference is Fish
		if(preference == ResourceType.FISH){			
			ArrayList<Point> FishList = theMap.getFishList();
			int size = FishList.size();
					
			for(int i = 0; i < size; i++){

			int FishX = FishList.get(i).x;
			int FishY = FishList.get(i).y;
			
			double distanceToResource = Math.sqrt(Math.pow((XPos - FishX),2) + Math.pow((YPos - FishY),2));
			
			if(distanceToResource < distance){
				distance = distanceToResource;
				closest = new Point(FishX,FishY);
			}
			}
		}

		//for if the preference is Tree
		if(preference == ResourceType.TREE){			
			ArrayList<Point> TreeList = theMap.getTreeList();
			int size = TreeList.size();
					
			for(int i = 0; i < size; i++){

			int TreeX = TreeList.get(i).x;
			int TreeY = TreeList.get(i).y;
			
			double distanceToResource = Math.sqrt(Math.pow((XPos - TreeX),2) + Math.pow((YPos - TreeY),2));
			
			if(distanceToResource < distance){
				distance = distanceToResource;
				closest = new Point(TreeX,TreeY);
			}
			}
		}
		
		//for if the preference is Tree
		if(preference == ResourceType.STONE){			
			ArrayList<Point> StoneList = theMap.getStoneList();
			int size = StoneList.size();
					
			for(int i = 0; i < size; i++){

			int StoneX = StoneList.get(i).x;
			int StoneY = StoneList.get(i).y;
			
			double distanceToResource = Math.sqrt(Math.pow((XPos - StoneX),2) + Math.pow((YPos - StoneY),2));
			
			if(distanceToResource < distance){
				distance = distanceToResource;
				closest = new Point(StoneX,StoneY);
			}
			}
		}
	}
	//sends the worker to harvest the closest of preference if they are idle
	//help Kyle, I don't know how to call the method to make him go to the place
	//pretty please
	
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

