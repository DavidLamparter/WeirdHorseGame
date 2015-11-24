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
|  Description:  This program is the queue for workers to take jobs from
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;

public class WorkQueue extends Observable {
	private ArrayList<Job> queue = new ArrayList<>();
	
	public WorkQueue() {
		
	}
	public void add(Job toAdd) {
		queue.add(toAdd);
		setChanged();
		notifyObservers(this);
	}
	public ArrayList<Job> getQueue() {
		return queue;
	}
	//  for those lazy sons of bitches
	//  origin is the worker
	public Job findClosest(Point origin) {
		double min = Double.MAX_VALUE;
		Point minPoint = queue.get(0).getLocation();
		Resource res = queue.get(0).getResource();
		for(int i = 0; i < queue.size(); i++) {
			double potentialMin = origin.distance(queue.get(i).getLocation());
			if(potentialMin < min) {
				minPoint = queue.get(i).getLocation();
				res = queue.get(i).getResource();
			}
		}
		return new Job(minPoint, res);
		
	}
	
	//  for the ambitious types
	public Job getFirst() {
		try {
		Job temp = queue.get(0);
		queue.remove(0);
		setChanged();
		notifyObservers(this);
		return temp;
		}
		catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	//  for the relativly lazy
	//  origin is the workers location
	public Job getRelativeCloseness(Point origin) {
		double min = origin.distance(queue.get(0).getLocation());
		Resource jobTitle = queue.get(0).getResource();
		Point minPoint = queue.get(0).getLocation();
		int tightness = 6;  //  tightness is how close they will try to be. Ex a tree 10 ft away and a fish 5 ft away
		//  with tightness of 6 it will do the 5 ft away first
		double originalDistance = origin.distance(queue.get(0).getLocation());
		for(int i = 0; i < queue.size(); i++) {
			double potentialMin = origin.distance(queue.get(i).getLocation());
			if(potentialMin < min) {
				if(potentialMin >= originalDistance - tightness) {
					minPoint = queue.get(i).getLocation();
					jobTitle = queue.get(i).getResource();
				}
			}
		}
		return new Job(minPoint, jobTitle); 
	}
	public void removeJob(Job toRemove) {
		for(int i = 0; i < queue.size(); i++) {
			if(queue.get(i).getLocation().equals(toRemove.getLocation())) {
				queue.remove(i);
				System.out.println("Removed Bro");
				setChanged();
				notifyObservers(this);
				break;
			}
		}
	}
	public boolean isEmpty() {
		return queue.isEmpty();
	}
	public int size() {
		return queue.size();
	}
}
