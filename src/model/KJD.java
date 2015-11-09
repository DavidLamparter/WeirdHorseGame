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
|  Description:  This program creates the "Kyle James DeTar" worker class that gets hungry more easily
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package model;

public class KJD extends Worker {
	
	public KJD() {
		super();
	}
	
	@Override
	public void addHunger() {
		// If hunger rises above 10, this worker may die from starvation
		if(getHunger() >= 10) {
			inDanger(getHunger());
		}
		addHunger(1.5);
	}
}
