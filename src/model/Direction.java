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
	public static Direction invert(Direction dir) {
		if(dir == NORTH)
			return SOUTH;
		if(dir == SOUTH)
			return NORTH;
		if(dir == EAST)
			return WEST;
		return EAST;
	}
	public static Direction rotateLeft(Direction dir) {
		if(dir == NORTH)
			return WEST;
		if(dir == WEST)
			return SOUTH;
		if(dir == SOUTH)
			return EAST;
		return NORTH;
	}
	public static Direction rotateRight(Direction dir) {
		if(dir == NORTH)
			return EAST;
		if(dir == EAST)
			return SOUTH;
		if(dir == SOUTH)
			return WEST;
		return NORTH;
	}
	public static Direction getRandom() {
		double num = Math.random();
		if(num < .25)
			return NORTH;
		else if(num < .50)
			return WEST;
		else if(num < .75)
			return SOUTH;
		return EAST;
	}
}
