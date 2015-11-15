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
|  Description:  This program creates the "Kyle James Grady" worker class that gets cold more easily
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package model;

import java.awt.Point;

public class KJG extends Worker{

	public KJG(Point currentLocation) {
		super(currentLocation);
	}
	
	@Override
	public void addColdness() {
		// If coldness rises above 10, this worker may die from frostbite
		if(getColdness() >= 10) {
			inDanger(getColdness());
		}
		addColdness(1.5);
	}
}
