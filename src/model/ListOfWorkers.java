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

import java.awt.Point;

public class ListOfWorkers {
	private Worker[] theWorkmen;
	private int pos;
	private int initialSize;
	
	public ListOfWorkers(int length) {
		theWorkmen = new Worker[length];
		initialSize = length;
	}
	//  adds a worker to the end of the array
	public void add(Worker toAdd) {
		if(pos < 0)
			pos ++;
		theWorkmen[pos] = toAdd;
		pos++;
		if(pos>=theWorkmen.length) {
			resize();
		}
	}
	//  removes the worker and then shifts everything left
	public void removeAt(int i) {
		if(pos >= 0) { 
		for(int j = i; j < pos - 1; j++)
			theWorkmen[i] = theWorkmen[i+1];
		pos--;
		}
	}
	//  removes all of the dead workers from the list
	public void removeDead() {
		for(int i = 0; i < pos; i++) {
			if(!theWorkmen[i].isAlive())
				removeAt(i);
		}
	}
	//  uses the distance formula to find the closest worker who is currently not busy with another task
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
		//  however it could return null meaning there are no workers who meet these qualifications
		return closest;
	}
	//  gets the worker at element i or null;
	public Worker get(int i) {
		if(i < pos)
			return theWorkmen[i];
		return null;
	}
	public int size() {
		return pos;
	}
	public void incrementEverything() {
		incrementHunger();
		incrementColdness();
		incrementSleep();
	}
	//  increments the hunger of every worker
	public void incrementHunger() {
		for(int i = 0; i < pos; i++) {
			theWorkmen[i].addHunger();
		}
	}
	//  makes every worker chilly
	public void incrementColdness() {
		for(int i = 0; i < pos; i++) {
			theWorkmen[i].addColdness();
		}
	}
	//  makes every worker tired
	public void incrementSleep() {
		for(int i = 0; i < pos; i++) {
			theWorkmen[i].addFatigue();
		}
	}
	//  makes every worker move
	public void moveYourAsses() {
		for(int i = 0; i < pos; i++) {
			theWorkmen[i].move();
		}
	}
	//  resises the array to make it 10 larger or the original size
	//  Might remove depending on how strict the total number of workers is
	private void resize() {
		if(pos == theWorkmen.length) {
			Worker[] resizeable = new Worker[theWorkmen.length+10];
			for(int i = 0; i < theWorkmen.length; i++) {
				resizeable[i] = theWorkmen[i];
			}
			theWorkmen = resizeable;
		}
		else if (pos < initialSize) {
			Worker[] resizeable = new Worker[initialSize];
			for(int i = 0; i < theWorkmen.length; i++) {
				resizeable[i] = theWorkmen[i];
			}
			theWorkmen = resizeable;
		}
	}
}
