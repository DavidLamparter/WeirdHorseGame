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
|  Description:  This program creates the "David" worker class that gets hungry more easily
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package model;

import java.awt.Point;

public class David extends Worker {

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
