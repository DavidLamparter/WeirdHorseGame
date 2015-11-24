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
|  Description:  This program creates an array of all living workers to be used and referenced by
|                other classes.
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package model;

import java.awt.Point;
import java.util.Iterator;

public class ListOfWorkers{
	
	/**************************************
	 *          Instance Variables        *
	 **************************************/
	
	// Array to hold workers
	private Worker[] theWorkmen;
	
	// This int represents the next empty slot of theWorkmen
	private int pos;
	
	/**************************************
	 *          Worker Constructor        *
	 **************************************/
	
	public ListOfWorkers(int length) {
		theWorkmen = new Worker[length];
		pos = 0;
	}
	
	/**************************************
	 *   Getters for Instance Variables   *
	 **************************************/
	
	// Gets the worker at element "i" if possible
	// else returns null
	public Worker get(int i) {
		if((i >= 0) && (i < pos))
			return theWorkmen[i];
		return null;
	}
		
	// Returns pos which is the number of living workers
	public int size() {
		return pos;
	}
	
	/**************************************
	 *         Add / Remove Worker        *
	 **************************************/
	
	// Adds a worker to the next available slot
	public void add(Worker toAdd) {
		theWorkmen[pos] = toAdd;
		pos++;
		
		// If we've filled the array, grow it
		if(pos == theWorkmen.length) {
			reSize();
		}
	}
	
	// Removes the worker and then shifts everything left
	public void removeAt(int i) {
		
		// Ensure we're removing at a valid index
		if((i >= 0) && (i < pos)) { 
			for(int j = i; j < (pos-1); j++)
				theWorkmen[i] = theWorkmen[i+1];
			pos--;
		}
	}
	//  gets the worker at the point
	public Worker getAt(Point point) {
		for(int i = 0; i < pos; i++) {
			if(theWorkmen[i].getPoint().equals(point))
				return theWorkmen[i];
		}
		return null;
	}
	// Removes all of the dead workers from the list
	public void removeDead() {
		for(int i = 0; i < pos; i++) {
			if(!theWorkmen[i].isAlive())
				removeAt(i);
		}
	}
	
	/**************************************
	 *             findClosest            *
	 **************************************/
	
	// Uses the distance formula to find the closest worker who is currently not busy with another task
	public Worker findClosest(Point theSnacks) {
		double min = Double.MAX_VALUE;
		Worker closest = null;		
		for(int i = 0; i < pos; i++) {
			double potentialMin = 
			Math.sqrt(Math.pow(theWorkmen[i].getX()-theSnacks.x,2) +
			Math.pow(theWorkmen[i].getY()-theSnacks.y,2));
			if((potentialMin < min)&&(!theWorkmen[i].isBusy())
					&&(theWorkmen[i].isAlive())) {
				closest = theWorkmen[i];
				min = potentialMin;
			}
		}
		// However it could return null meaning there are no workers who meet these qualifications
		return closest;
	}
	
	/**************************************
	 *         findANotBusyGoon           *
	 **************************************/
	
	//  returns the first not busy worker
	public Worker findAnyone() {
		for(int i = 0; i < pos; i++) {
			if(!theWorkmen[i].isBusy()&&(theWorkmen[i].isAlive()))
				return theWorkmen[i];
		}
		return null;
	}
	
	/**************************************
	 *        Condition Incrementors      *
	 **************************************/
	
	// Increments all 3 conditions for every worker
	public void incrementConditions() {
		incrementHunger();
		incrementColdness();
		incrementFatigue();
	}
	
	// Increments the hunger of every worker
	public void incrementHunger() {
		for(int i = 0; i < pos; i++) {
			theWorkmen[i].addHunger();
		}
	}
	
	// Increments the coldness of every worker
	public void incrementColdness() {
		for(int i = 0; i < pos; i++) {
			theWorkmen[i].addColdness();
		}
	}
	
	// Increments the fatigue of every worker
	public void incrementFatigue() {
		for(int i = 0; i < pos; i++) {
			theWorkmen[i].addFatigue();
		}
	}
	
	/**************************************
	 *             moveWorkers            *
	 **************************************/
	
	// Calls the move method for every worker, allowing
	// them to move to the next desired tile
	public void moveWorkers() {
		for(int i = 0; i < pos; i++) {
			theWorkmen[i].move();
		}
	}
	
	/**************************************
	 *               reSize               *
	 **************************************/
	
	// Resizes the array by doubling its length
	private void reSize() {
		Worker[] resizeable = new Worker[pos * 2];
		for(int i = 0; i < theWorkmen.length; i++) {
			resizeable[i] = theWorkmen[i];
		}
		theWorkmen = resizeable;
	}
}
