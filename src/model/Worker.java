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

public abstract class Worker {
	
	/**************************************
	 *          Instance Variables        *
	 **************************************/
	
	// Conditions (bad stuff) begin at 0, and increment to dangerous levels
	private int hunger;
	private int fatigue;
	private int coldness;
	
	//  two things I added KG
	private int XPos;
	private int YPos;
	//  END OF THINGS I ADDED
	
	// Aspects (good/neutral stuff) begin at max and decrement to dangerous levels
	private int happiness;
	private int carryingCapacity;
	
	// Tool represents what tool the worker is holding
	private Tool tool;
	
	// isAlive will be set to false if the worker dies
	private boolean isAlive;
	
	/**************************************
	 *          Worker Constructor        *
	 **************************************/
	
	public Worker() {
		
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
	
	/**************************************
	 *          Setter for tool           *
	 **************************************/
	
	public void setTool(Tool tool) {
		this.tool = tool;
	}
	
	public void inDanger(int status) {
		double dubStatus = (((double) status) / 100 - 0.1) * Math.pow(1.25,(status));
		double rand = Math.random();
		if(rand > (0.66 - dubStatus))
			isAlive = false;
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
	 *       Points for Movements !!!     *
	 **************************************/
	private ArrayList<Direction> myTask = new ArrayList<>();
	private boolean isBusy;
	private Direction last;
	//  May need to have a way to cancel task, As of now
	//  workers know the directions to get somewhere but don't know what 
	//  they are doing. Lets send people to get food then say oops
	//  no need for that its a dumb resource how would they stop?
	public int getX() {
		return XPos;
	}
	public int getY() {
		return YPos;
	}
	public Direction getLast() {
		return last;
	}
	public boolean isBusy(){
		return false;
	}
	public void toLocation(ArrayList<Direction> directions) {
		myTask = directions;
	}
	public void move() {
		if(!myTask.isEmpty()) {
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
	}
}