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
|  Description:  This program creates the "David" worker class that is less motivated
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package model;

import java.awt.Point;

public class David extends Worker {

	public David(Point currentLocation) {
		super(currentLocation);
	}
	
	@Override
	public void subtractHappiness() {
		subtractHappiness(1.5);
	}
}
