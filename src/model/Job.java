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
|  Description:  DID YOU EVER WANT POINTS WITH NAMES???
|					NO???
|						Well that's what we got here
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/
package model;

import java.awt.Point;

public class Job {
	String name = "";
	Point location = null;
	
	public Job(Point location, String name){
		this.location = location;
		this.name = name;
		giveCoolName();
	}

	public String getName() {
		return name;
	}
	
	public Point getLocation() {
		return location;
	}
	
	private void giveCoolName() {
		if(name.equals("Tree")) {
			name = "Chop down ALL THE TREES";
		}
		if(name.equals("Stone")) {
			name = "Get Some Stone";
		}
		if(name.equals("Salty Fish")) {
			name = "Get to the salt!";
		}
	}
}
