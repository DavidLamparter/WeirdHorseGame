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
	public void add(Worker toAdd) {
		if(pos < 0)
			pos ++;
		theWorkmen[pos] = toAdd;
		pos++;
		if(pos>=theWorkmen.length) {
			resize();
		}
	}
	public void removeAt(int i) {
		if(pos >= 0) { 
		for(int j = i; j < pos - 1; j++)
			theWorkmen[i] = theWorkmen[i+1];
		pos--;
		}
	}
	public void removeDead() {
		for(int i = 0; i < pos; i++) {
			if(!theWorkmen[i].isAlive())
				removeAt(i);
		}
	}
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
		return closest;
	}
	public Worker get(int i) {
		return theWorkmen[i];
	}
	public int size() {
		return pos;
	}
	public void incrementEverything() {
		incrementHunger();
		incrementColdness();
		incrementSleep();
	}
	public void incrementHunger() {
		for(int i = 0; i < pos; i++) {
			theWorkmen[i].addHunger();
		}
	}
	public void incrementColdness() {
		for(int i = 0; i < pos; i++) {
			theWorkmen[i].addColdness();
		}
	}
	public void incrementSleep() {
		for(int i = 0; i < pos; i++) {
			theWorkmen[i].addFatigue();
		}
	}
	public void moveYourAsses() {
		for(int i = 0; i < pos; i++) {
			theWorkmen[i].move();
		}
	}
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
