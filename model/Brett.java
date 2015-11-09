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
|  Description:  This program creates the "Brett" worker class that gets tired more easily
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package model;

import model.Worker;

public class Brett extends Worker{
	
	public Brett() {
		super();
	}
	
	@Override
	public void addFatigue() {
		// If fatigue rises above 10, this worker may die from exhaustion
		if(getFatigue() >= 10) {
			inDanger(getFatigue());
		}
		addFatigue(1.5);
	}
}
