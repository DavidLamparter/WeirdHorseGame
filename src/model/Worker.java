/*================================================================================================
|   Assignment:  Project 5: Hunt The Wumpus
|      Authors:  David Lamparter (Lamparter@email.arizona.edu)
|                Kyle Grady (kgrady1@email.arizona.edu)     
|                       
|       Course:  335
|   Instructor:  R. Mercer
|     Due Date:  10/18/15
|
|  Description:  This program creates the Worker object that will represent our agents that can collect
|                resources.
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package model;
public class Worker {
	
	// Conditions (bad stuff) begin at 0, and increment to dangerous levels
	private int hunger;
	private int fatigue;
	private int coldness;
	
	// Aspects (good/neutral stuff) begin at max and decrement to dangerous levels
	private int happiness;
	private int carryingCapacity;
	
	// Tool represents what tool the worker is holding
	private Tool tool;
	
	// isAlive will be set to false if the worker dies
	private boolean isAlive;
	
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
	
	/*
	 * Getters for instance variables
	 */
	
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
	
	/*
	 * Setter for tool
	 */
	
	public void setTool(Tool tool) {
		this.tool = tool;
	}
	
	/*
	 * Adders for conditions
	 */
	
	public void addHunger() {
		hunger += 1;
	}
	
	public void addFatigue() {
		fatigue += 1;
	}
	
	public void addColdness() {
		coldness += 1;
	}
	
	/*
	 * Subtractors for aspects
	 */
	
	public void subtractHappiness() {
		happiness -= 1;
	}
	
	public void subtractCarryingCapacity() {
		carryingCapacity -= 1;
	}
	
}