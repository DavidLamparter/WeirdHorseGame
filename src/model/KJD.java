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
|  Description:  This program creates the "Kyle James DeTar" worker class that is less motivated
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package model;

import java.awt.Point;

public class KJD extends Worker {
	
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
