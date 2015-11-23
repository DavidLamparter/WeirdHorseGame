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
	Resource resource;
	Point location = null;
	
	public Job(Point location, Resource resource){
		this.location = location;
		this.resource = resource;
		//  giveCoolName();
	}

	public String getName() {
		return resource.getName();
	}
	
	public Resource getResource() {
		return resource;
	}
	
	public Point getLocation() {
		return location;
	}
	
	/*private void giveCoolName() {
		if(name.equals("Tree")) {
			name = "Chop down ALL THE TREES";
		}
		if(name.equals("Stone")) {
			name = "Get Some Stone";
		}
		if(name.equals("Salty Fish")) {
			name = "Get to the salt!";
		}
	}*/
}
