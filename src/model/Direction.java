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
|  Description:  This program . . .
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package model;
//  ENUMS
public enum Direction {
	NORTH, SOUTH, EAST, WEST;
	public Direction invert(Direction dir) {
		if(dir == NORTH)
			return SOUTH;
		if(dir == SOUTH)
			return NORTH;
		if(dir == EAST)
			return WEST;
		return EAST;
	}
}
